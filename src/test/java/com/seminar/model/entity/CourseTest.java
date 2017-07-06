package com.seminar.model.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.junit.Before;
import org.junit.Test;

import com.seminar.model.EntityModel;

public class CourseTest {

	private Course _course; 

	@Before
	public void setUp(){
		_course = new Course(1, "name", "description", "somewhere", 1, new Time("20.09.2016"));
	}
	
	@Test
	public void enrollingAStudentDecreaseSeatsLeft() {
		_course.enroll(new Student("a", "b"));
		
		assertThat(_course.getSeatsLeft(), is(0));
	}

	@Test
	public void cantEnrollStudentOverTotal() {
		try {
			_course.enroll(new Student("a", "b"));
			_course.enroll(new Student("c", "d"));
	        
			fail("Expected an RuntimeException to be thrown");
	    } catch (RuntimeException rte) {
	        assertThat(rte.getMessage(), is("Student: c d can't be enrolled. Seats terminated"));
	    }
	}
	
	@Test
	public void courseHasStartDate() throws Exception {
		assertThat(_course.getTime(), is(new Time("20.09.2016")));
	}
	
	@Test
	public void validCourse() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>(){{
			put("id", "123");
			put("name", "course");
			put("start", "26.03.2017");
			put("location", "somewhere");
			put("totalSeats", "15");
		}};
		EntityModel validCourse = new EntityModel(Course.class, Course.rules(), params);
		
		assertThat(validCourse.isValid(), is(true));
	}
	
	@Test
	public void courseNameStartLocationTotalSeatsAreMandatory() throws Exception {
		
		EntityModel invalidCourse = new EntityModel(Course.class, Course.rules(), new HashMap<String, String>());
		
		assertThat(invalidCourse.isValid(), is(false));
		assertThat(invalidCourse.isBrokenOn("name"), is(true));
		assertThat(invalidCourse.isBrokenOn("start"), is(true));
		assertThat(invalidCourse.isBrokenOn("location"), is(true));
		assertThat(invalidCourse.isBrokenOn("totalSeats"), is(true));
		assertThat(invalidCourse.isBrokenOn("description"), is(false));
	}
	
	@Test
	public void courseHasLengthAndNumericLimit() throws Exception {
		Map<String, String> params = new HashMap<String, String>(){{
			put("name", "xxxxxxxxxxxxxxxx");
			put("start", "26.03.2017");
			put("location", "xxxxxxxxxxxxxxxxxxxxxxx");
			put("totalSeats", "9999");
		}};
		
		MultiValuedMap<String, String> errors = new EntityModel(Course.class, Course.rules(), params).validate();
		
		assertThat(errors.get("name"), contains("must have no more than 15 chars"));
		assertThat(errors.get("location"), contains("must have no more than 20 chars"));
		assertThat(errors.get("totalSeats"), contains("must be less than 100", "must have no more than 3 chars"));
	}
}
