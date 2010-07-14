package com.jklas.search.engine.filter;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.jklas.search.annotations.Indexable;
import com.jklas.search.annotations.SearchField;
import com.jklas.search.annotations.SearchFilter;
import com.jklas.search.annotations.SearchId;
import com.jklas.search.engine.BooleanSearch;
import com.jklas.search.engine.dto.ObjectKeyResult;
import com.jklas.search.index.memory.MemoryIndexReader;
import com.jklas.search.query.bool.BooleanQuery;
import com.jklas.search.query.bool.BooleanQueryParser;
import com.jklas.search.util.Utils;

public class DateRangeFilterTest {
	
	@SuppressWarnings("unused")
	@Indexable
	private class Entity {
		@SearchId public final int id;

		@SearchField public final String attribute;

		@SearchFilter public final Date dateOfBirth;

		public Entity(int id, String attribute, Date dateOfBirth) {
			this.id = id;
			this.attribute = attribute;
			this.dateOfBirth = dateOfBirth;
		}		
	}

	@Test
	public void EntitiesNotInDateIntervalAreDiscarded() throws SecurityException, NoSuchFieldException {
		Calendar julianBirthCalendar = Calendar.getInstance();	
		julianBirthCalendar.set(Calendar.YEAR,1983);
		julianBirthCalendar.set(Calendar.MONTH,04);
		julianBirthCalendar.set(Calendar.DAY_OF_MONTH,30);
		julianBirthCalendar.set(Calendar.MINUTE,0);
		julianBirthCalendar.set(Calendar.SECOND,0);
		julianBirthCalendar.set(Calendar.MILLISECOND,0);

		Entity julian   = new Entity(0,"julian", julianBirthCalendar.getTime());

		Calendar juanBirthCalendar = Calendar.getInstance();
		juanBirthCalendar.set(Calendar.YEAR,1993);
		juanBirthCalendar.set(Calendar.MONTH,04);
		juanBirthCalendar.set(Calendar.DAY_OF_MONTH,1);
		juanBirthCalendar.set(Calendar.MINUTE,0);
		juanBirthCalendar.set(Calendar.SECOND,0);
		juanBirthCalendar.set(Calendar.MILLISECOND,0);

		Entity juan 	= new Entity(1,"juan", juanBirthCalendar.getTime());

		Utils.setupSampleMemoryIndex(julian,juan);

		BooleanQueryParser parser = new BooleanQueryParser("julian +OR juan");
		BooleanQuery query = parser.getQuery();
		BooleanSearch booleanSearch = new BooleanSearch(query, new MemoryIndexReader());		
		Set<ObjectKeyResult> results = booleanSearch.search();

		Assert.assertEquals(2, results.size() );

		Calendar start = Calendar.getInstance(), end = Calendar.getInstance();
		start.set(Calendar.YEAR,1983);
		start.set(Calendar.MONTH,03);
		start.set(Calendar.DAY_OF_MONTH,30);
		start.set(Calendar.MINUTE,0);
		start.set(Calendar.SECOND,0);
		start.set(Calendar.MILLISECOND,0);

		end.set(Calendar.YEAR,1990);
		end.set(Calendar.MONTH,01);
		end.set(Calendar.DAY_OF_MONTH,1);
		end.set(Calendar.MINUTE,0);
		end.set(Calendar.SECOND,0);
		end.set(Calendar.MILLISECOND,0);

		DateRangeFilter dateOfBirthFilter = new DateRangeFilter(start.getTime(), end.getTime(), Entity.class.getDeclaredField("dateOfBirth"));

		for (Iterator<ObjectKeyResult> iterator = results.iterator(); iterator.hasNext();) {
			ObjectKeyResult currentResult = (ObjectKeyResult) iterator.next();
			if(dateOfBirthFilter.isFiltered(currentResult)) iterator.remove();
		}

		Assert.assertEquals(1, results.size() );
	}


}
