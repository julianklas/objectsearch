/**
 * Object Search Framework
 *
 * Copyright (C) 2010 Julian Klas
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.jklas.search.engine.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jklas.search.engine.Language;
import com.jklas.search.engine.operations.NoWordCleaner;
import com.jklas.search.engine.operations.SpaceTokenizer;
import com.jklas.search.engine.operations.StemmingOperation;
import com.jklas.search.engine.operations.UpperCaseNoSymbolTextNormalizer;
import com.jklas.search.engine.stemming.IdentityStemmerStrategy;
import com.jklas.search.engine.stemming.StemType;
import com.jklas.search.engine.stemming.StemmerStrategy;
import com.jklas.search.index.Term;

public class NormalizeTokenizeProcessor implements QueryTextProcessor {

	private static final NoWordCleaner noWordProcessor = new NoWordCleaner();

	private static final UpperCaseNoSymbolTextNormalizer textNormalizer = new UpperCaseNoSymbolTextNormalizer("+");

	private static final SpaceTokenizer tokenizer = new SpaceTokenizer();

	private static final StemmingOperation stemmingOperation = new StemmingOperation();
	
	private final StemmerStrategy stemmerStrategy ;

	private final StemType stemType;
	
	public NormalizeTokenizeProcessor() {
		stemmerStrategy = new IdentityStemmerStrategy();
		this.stemType = StemType.NO_STEM;
	}
	
	public NormalizeTokenizeProcessor(StemmerStrategy stemmerStrategy, StemType stemType) {
		this.stemmerStrategy = stemmerStrategy;
		this.stemType = stemType;
	}
	
	@Override
	public List<Term> processText(String text, Language language) {

		String normalizedText = textNormalizer.normalizeExpression(text);

		List<Term> tokens = tokenizer.tokenize(normalizedText);

		noWordProcessor.deleteStopWords(tokens, language);

		if(tokens.size() == 0) Collections.emptyList();

		List<Term> fieldTokens = new ArrayList<Term>();

		for (Term token : tokens) {

			Term stemmedToken = stemmingOperation.applyStemming(token, language, this.stemmerStrategy, this.stemType);

			fieldTokens.add(stemmedToken);
		}

		return fieldTokens;		
	}

}