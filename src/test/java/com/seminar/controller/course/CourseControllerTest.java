package com.seminar.controller.course;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.Context;
import com.Db;
import com.FakeRequest;
import com.FakeResponse;
import com.Route;
import com.seminar.model.entity.Course;
import com.seminar.model.entity.Time;
import com.seminar.model.mapper.CourseMapper;

public class CourseControllerTest {

	private FakeResponse _response;

	@Before
	public void setUp() {
		_response = new FakeResponse();
	}
	
	@After
	public void tearDown(){
		Db.close();
	}
	
	@Test
	public void handlesItsOwnRoutes() throws Exception {
		assertTrue(new CourseController().handles("/course"));
		assertTrue(new CourseController().handles("/course/"));
		assertTrue(new CourseController().handles("/course/create"));
		assertTrue(new CourseController().handles("/course/create/"));
		assertTrue(new CourseController().handles("/"));

		assertFalse(new CourseController().handles("/some/1"));
		assertFalse(new CourseController().handles("/courseany"));
	}
	
	@Test
	public void createNewCourse() throws Exception {
		new CourseController().execute(context(Create.ROUTE, "POST", defaultRequestParams()));
		
		assertThat(_response.status(), is(HttpServletResponse.SC_FOUND));
		assertThat(_response.getHeader("Location"), is(AllCourse.ROUTE.toString()));
		assertThat(_response.content(), containsString("courseName"));
	}

	@Test
	public void updateCourse() throws Exception {
		insertCourseWithName("courseToUpdate");
		
		Map<String, String> parameters = defaultRequestParams();
		parameters.put("id", "1");
		parameters.put("name", "updatedName");
		
		new CourseController().execute(context(Create.ROUTE, "POST", parameters));
		
		assertThat(_response.status(), is(HttpServletResponse.SC_FOUND));
		assertThat(_response.getHeader("Location"), is(AllCourse.ROUTE.toString()));
		assertThat(_response.content(), containsString("updatedName"));
	}
	
	@Test
	public void deleteCourse() throws Exception {
		insertCourseWithName("courseToDelete");
		
		new CourseController().execute(context(new Route("/course/delete/1"), "GET"));
		
		assertThat(_response.status(), is(HttpServletResponse.SC_FOUND));
		assertThat(_response.getHeader("Location"), is(AllCourse.ROUTE.toString()));
		assertThat(_response.content(), not(containsString("courseToDelete")));
	}

	@Test
	public void renderCourseList() throws Exception {
		new CourseController().execute(context(AllCourse.ROUTE, "GET"));
		
		assertThat(_response.content(), containsString("<thead>"));
	}
	
	@Test
	public void renderCreationForm() throws Exception {
		new CourseController().execute(context(Create.ROUTE, "GET"));
		
		assertThat(_response.content(), containsString("<form class='form-horizontal' method='post' action='" + Create.ROUTE + "'>"));
	}
	
	@Test
	public void wrongCreationGivesFeedbackToUser() throws Exception {
		Map<String, String> parameters = new HashMap<String, String>(){{
			put("name", "");
			put("description", "desc");
			put("id", "0");
			put("location", "loc");
			put("totalSeats", "");
			put("start", "wrong format");
		}};
		
		new CourseController().execute(context(Create.ROUTE, "POST", parameters));
		
		assertThat(_response.content(), containsString("can't be empty"));
		assertThat(_response.content(), containsString("must be a number"));
		assertThat(_response.content(), containsString("must have dd.MM.yyyy format"));
	}
	
	private void insertCourseWithName(String name) {
		Course course = new Course(null, name, "description", "location", 10, new Time());
		new CourseMapper(Db.connection()).insert(course);
	}
	
	private Map<String, String> defaultRequestParams() {
		return new HashMap<String, String>(){{
			put("name", "courseName");
			put("description", "desc");
			put("id", "");
			put("location", "loc");
			put("totalSeats", "15");
			put("start", "10.10.2017");
		}};
	}
	
	private Context context(Route route, String method, Map<String, String> requestParams){
		return new Context(new FakeRequest(route, method, requestParams), _response, Db.connection());
	}
	
	private Context context(Route route, String method){
		return context(route, method, new HashMap<String, String>());
	}
}
