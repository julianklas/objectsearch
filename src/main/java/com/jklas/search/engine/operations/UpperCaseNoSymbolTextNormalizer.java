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
package com.jklas.search.engine.operations;

import com.jklas.search.util.TextLibrary;

public class UpperCaseNoSymbolTextNormalizer {

	private final static char[][] accentedVowelMap = {{'á','é','í','ó','ú','Á','É','Í','Ó','Ú'},{'a','e','i','o','u','A','E','I','O','U'}};
		
	private final String[] exceptions;
	
	private final boolean useExceptions;
	
	public UpperCaseNoSymbolTextNormalizer() {
		this.useExceptions =false;
		this.exceptions = null;
	}
	
	public UpperCaseNoSymbolTextNormalizer(String... exceptions) {
		if(exceptions == null) throw new IllegalArgumentException("Can't work with a null list of exceptions... use default constructor instead");
		this.useExceptions = true;
		this.exceptions = exceptions;
	}

	public String normalizeExpression(String text) {
		
		text = TextLibrary.translate(text, accentedVowelMap);
		
		if(useExceptions)
			return TextLibrary.cleanSymbols(text,exceptions).toUpperCase();
		else
			return TextLibrary.cleanSymbols(text).toUpperCase();
	}
	
}
