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
package com.jklas.search.engine;

import java.util.Collection;
import java.util.Comparator;

import com.jklas.search.engine.dto.ObjectResult;
import com.jklas.search.engine.filter.FilterChain;
import com.jklas.search.sort.PreSort;

/**
 * This interface defines the Search tasks done by 
 * this framework.
 * 
 * @author Julián Klas
 *
 */
public interface Search {

	public abstract Collection<? extends ObjectResult> search();

	public abstract Collection<? extends ObjectResult> search(FilterChain filterChain);

	public abstract Collection<? extends ObjectResult> search(Comparator<? super ObjectResult> comparator);

	public abstract Collection<? extends ObjectResult> search(PreSort rule);

}