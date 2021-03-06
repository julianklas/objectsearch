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
package com.jklas.search.indexer.semionline;

import java.util.List;

import com.jklas.search.exception.IndexObjectException;
import com.jklas.search.index.dto.IndexObject;
import com.jklas.search.indexer.IndexerAction;
import com.jklas.search.indexer.IndexerService;
import com.jklas.search.util.SearchLibrary;

// All methods must be thread safe
public class SemiOnlineIndexer implements IndexerService {
	
	private final SemiOnlineWorkerPool workerPool;
	
	private boolean isDestroying = false;
	
	public SemiOnlineIndexer(SemiOnlineWorkerPool workerPool) {
		this.workerPool = workerPool;
	}
	
	@Override
	public void create(Object entity) throws IndexObjectException {
		checkForDestroy();
		this.workerPool.newTask(IndexerAction.CREATE, new IndexObject(entity));
	}

	@Override
	public void create(IndexObject indexObjectDto) throws IndexObjectException {
		checkForDestroy();
		this.workerPool.newTask(IndexerAction.CREATE, indexObjectDto);
	}

	@Override
	public void createOrUpdate(Object entity) throws IndexObjectException {
		checkForDestroy();
		this.workerPool.newTask(IndexerAction.CREATE_OR_UPDATE, new IndexObject(entity));
	}

	@Override
	public void createOrUpdate(IndexObject indexObjectDto) throws IndexObjectException {
		checkForDestroy();
		this.workerPool.newTask(IndexerAction.CREATE_OR_UPDATE, indexObjectDto);
	}

	@Override
	public void delete(Object entity) throws IndexObjectException {
		checkForDestroy();
		this.workerPool.newTask(IndexerAction.DELETE, new IndexObject(entity));
	}

	@Override
	public void delete(IndexObject indexObjectDto) throws IndexObjectException {
		checkForDestroy();
		this.workerPool.newTask(IndexerAction.DELETE, indexObjectDto);
	}

	@Override
	public void update(Object entity) throws IndexObjectException {
		checkForDestroy();
		this.workerPool.newTask(IndexerAction.UPDATE, new IndexObject(entity));
	}

	@Override
	public void update(IndexObject indexObjectDto) throws IndexObjectException {
		checkForDestroy();
		this.workerPool.newTask(IndexerAction.UPDATE, indexObjectDto);
	}
	
	public void destroy() {
		checkForDestroy();
		isDestroying = true;
		workerPool.destroy();
	}

	public void destroyWhenFinished() {
		checkForDestroy();
		isDestroying = true;
		workerPool.destroyWhenFinished();
	}

	private void checkForDestroy() {
		if(isDestroying) throw new IllegalStateException("This indexer is in process of destruction," +
				" can't accept any new objects to index");
	}

	public void waitForUnfinishedWorks() throws InterruptedException {
		workerPool.waitForAllWorkersToFinish();
	}

	public void enableWait() {
		workerPool.enableWait();
	}

	@Override
	public void bulkCreate(List<?> entities) throws IndexObjectException {		
		for (Object entity : entities) {
			create(entity);
		}
	}

	@Override
	public void bulkCreateOrUpdate(List<?> entities) throws IndexObjectException {
		for (Object entity : entities) {
			createOrUpdate(entity);
		}
	}

	@Override
	public void bulkDelete(List<?> entities) throws IndexObjectException {
		for (Object entity : entities) {
			delete(entity);
		}
	}

	@Override
	public void bulkDtoCreate(List<IndexObject> indexObjectDto) throws IndexObjectException {
		bulkCreate(SearchLibrary.convertDtoListToEntityList(indexObjectDto));
	}

	@Override
	public void bulkDtoCreateOrUpdate(List<IndexObject> indexObjectDto) throws IndexObjectException {
		bulkCreateOrUpdate(SearchLibrary.convertDtoListToEntityList(indexObjectDto));
	}

	@Override
	public void bulkDtoDelete(List<IndexObject> indexObjectDto) throws IndexObjectException {
		bulkDelete(SearchLibrary.convertDtoListToEntityList(indexObjectDto));
	}

	@Override
	public void bulkDtoUpdate(List<IndexObject> indexObjectDto) throws IndexObjectException {
		bulkUpdate(SearchLibrary.convertDtoListToEntityList(indexObjectDto));
	}

	@Override
	public void bulkUpdate(List<?> entities) throws IndexObjectException {
		for (Object entity : entities) {
			update(entity);
		}
	}

}
