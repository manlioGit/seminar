package com.seminar.model;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.Test;

import com.seminar.model.rule.MaxLength;
import com.seminar.model.rule.NotEmpty;
import com.seminar.model.rule.Rule;

public class EntityModelTest {

	private final MultiValuedMap<String, Rule> rules = new ArrayListValuedHashMap<String, Rule>(){{
		put("a", new NotEmpty());
		put("b", new NotEmpty());
		putAll("c", asList( new NotEmpty(), new MaxLength(-1)));
	}};
	
	@Test
	public void collectErrorForEveryInfrigment() throws Exception {
		Map<String, String> request = new HashMap<String, String>(){{
			put("a", "");
			put("b", "xxx");
			put("c", "");
		}};
		
		MultiValuedMap<String, String> errors = new EntityModel(null, rules, request).validate();
		
		assertThat(errors.get("a"), contains(new NotEmpty().message()));
		assertThat(errors.get("b"), is(empty()));
		assertThat(errors.get("c"), contains( new NotEmpty().message(), new MaxLength(-1).message()));
	}
	
	@Test
	public void createEntity() throws Exception {
		Map<String, String> request = new HashMap<String, String>(){{
			put("some", "xxx");
			put("thing", "123");
		}};
		
		 AnEntity anEntity = new EntityModel(AnEntity.class, rules, request).<AnEntity>create();
		 
		 assertThat(anEntity.some, is("xxx"));
		 assertThat(anEntity.thing, is(123));
	}
	
	@Test
	public void emptyRequest() throws Exception {
		MultiValuedMap<String, String> errors = new EntityModel(null, rules, new HashMap<String, String>()).validate();
		
		assertThat(errors.get("a"), contains(new NotEmpty().message()));
		assertThat(errors.get("c"), contains(new NotEmpty().message(), new MaxLength(-1).message()));
	}
}
