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

import java.util.List;

import com.jklas.search.configuration.MappedFieldDescriptor;
import com.jklas.search.engine.operations.StopWordCleaner;
import com.jklas.search.index.Term;

public interface ObjectTextProcessor {

	public static final Class<? extends ObjectTextProcessor> DFLT_TEXT_PROCESSOR = DefaultObjectTextProcessor.class;
	
	public static final Class<? extends ObjectTextProcessor> NULL_PROCESSOR = NullProcessor.class;
	
	public abstract List<Term> processField(String extractedText, MappedFieldDescriptor fieldDescriptor);

	public abstract StopWordCleaner getStopWordCleaner();
}
