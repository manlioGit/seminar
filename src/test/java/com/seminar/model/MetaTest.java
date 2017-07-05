package com.seminar.model;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.junit.Test;

import com.seminar.model.entity.Entity;
import com.seminar.model.entity.Time;

public class MetaTest {
	
	class Something implements Entity {
		private final  Integer _a = null;
		private final  Integer _b = null;
		private final  Date _c = null;
		private final  List<String> _collection = null;
		private final  MultiValuedMap<String, String> _weirdCollection = null; 
	}
	
	@Test
	public void signatureRemoveUnderscore() throws Exception {
		Iterable<String> expected = asList("a", "b", "c", "d");
		
		assertThat(Meta.signatureOf(anEntity().getClass()), is(expected));
	}
	
	@Test
	public void signatureDontConsiderCollectionType() throws Exception {
		Iterable<String> expected = asList("a", "b", "c");
		
		assertThat(Meta.signatureOf(Something.class), is(expected));
	}
	
	@Test
	public void convertEntityToMap() throws Exception {
		Map<String, String> expected = new HashMap<String, String>() {{
			put("_a", "wonderful value");
			put("b", "0");
			put("_c", "10.10.2017");
			put("d", "123");
		}};
		
		assertThat(Meta.toMap(anEntity()), is(expected));
	}

	private Entity anEntity() {
		return new Entity() {
			private final String _a = "wonderful value";
			private final Integer b = 0;
			private final Time _c = new Time("10.10.2017");
			private final BigDecimal d = new BigDecimal(123);
		};
	}
}
