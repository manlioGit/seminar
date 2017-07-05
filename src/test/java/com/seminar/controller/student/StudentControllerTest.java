package com.seminar.controller.student;

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
import com.seminar.model.entity.Student;
import com.seminar.model.mapper.StudentMapper;

public class StudentControllerTest {
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
		assertTrue(new StudentController().handles("/student"));
		assertTrue(new StudentController().handles("/student/"));
		assertTrue(new StudentController().handles("/student/create"));
		assertTrue(new StudentController().handles("/student/create/"));
		assertTrue(new StudentController().handles("/student/1"));
		assertTrue(new StudentController().handles("/student/update"));
		assertTrue(new StudentController().handles("/student/delete/1"));

		assertFalse(new StudentController().handles("/student/delete/"));
		assertFalse(new StudentController().handles("/student/delete"));
		assertFalse(new StudentController().handles("/studentany"));
	}
	
	@Test
	public void getAllShowsAllStudents() throws Exception {
		insertStudentWith("studentName");
		
		new StudentController().execute(context(StudentController.ALL, "GET"));
		
		assertThat(_response.content(), containsString("studentName"));
	}
	
	@Test
	public void createPageSubmittingGetShowEmptyForm() throws Exception {
		
		new StudentController().execute(context(StudentController.CREATE, "GET"));
		
		assertThat(_response.content(), containsString("firstName"));
		assertThat(_response.content(), containsString("lastName"));
	}
	
	@Test
	public void createRespectingRules() throws Exception {
		Map<String, String> studentMap = new HashMap<String, String>(){{
			put("firstName", "studentName");
			put("lastName", "studentLastName");
		}};
		
		new StudentController().execute(context(StudentController.CREATE, "POST", studentMap));
		
		assertThat(_response.status(), is(HttpServletResponse.SC_FOUND));
		assertThat(_response.getHeader("Location"), is(StudentController.ALL.toString()));
		assertThat(_response.content(), containsString("studentName"));
	}
	
	@Test
	public void createShowErrorWhenRulesAreBroken() throws Exception {
		Map<String, String> studentMap = new HashMap<String, String>(){{
			put("firstName", "aVeryVeryLongFirstName");
			put("lastName", "studentLastName");
		}};
		
		new StudentController().execute(context(StudentController.CREATE, "POST", studentMap));
		
		assertThat(_response.content(), containsString("must have no more than 15 chars"));
	}
	
	@Test
	public void updatePostModifyExistingStudent() throws Exception {
		insertStudentWith("toUpdate");
		Map<String, String> studentMap = new HashMap<String, String>(){{
			put("id", "1");
			put("firstName", "updatedName");
			put("lastName", "studentLastName");
		}};
		
		new StudentController().execute(context(StudentController.UPDATE, "POST", studentMap));
		
		assertThat(_response.status(), is(HttpServletResponse.SC_FOUND));
		assertThat(_response.getHeader("Location"), is(StudentController.ALL.toString()));
		assertThat(_response.content(), containsString("updatedName"));
	}
	
	@Test
	public void updateGetShowFormWithData() throws Exception {
		insertStudentWith("toUpdate");
		
		new StudentController().execute(context(new Route("/student/1"), "GET"));

		assertThat(_response.content(), containsString("action='" + StudentController.UPDATE + "'"));
		assertThat(_response.content(), containsString("toUpdate"));
	}
	
	@Test
	public void updatePostShowErrorsWhenRuleAreBroken() throws Exception {
		insertStudentWith("toUpdate");
		Map<String, String> studentMap = new HashMap<String, String>(){{
			put("id", "1");
			put("firstName", "aVeryVeryLongFirstNname");
			put("lastName", "studentLastName");
		}};
		
		new StudentController().execute(context(StudentController.UPDATE, "POST", studentMap));
		
		assertThat(_response.content(), containsString("must have no more than 15 chars"));
		assertThat(_response.content(), containsString("hidden='hidden'"));
		assertThat(_response.content(), containsString("id='id'"));
	}
	
	@Test
	public void delete() throws Exception {
		insertStudentWith("toDelete");
		
		new StudentController().execute(context(new Route("/student/delete/1"), "GET"));
		
		assertThat(_response.status(), is(HttpServletResponse.SC_FOUND));
		assertThat(_response.getHeader("Location"), is(StudentController.ALL.toString()));
		assertThat(_response.content(), not(containsString("toDelete")));
	}
	
	private void insertStudentWith(String name) {
		new StudentMapper(Db.connection()).insert(new Student(name, "lastName"));
	}
	
	private Context context(Route route, String method, Map<String, String> requestParams){
		return new Context(new FakeRequest(route, method, requestParams), _response, Db.connection());
	}
	
	private Context context(Route route, String method){
		return context(route, method, new HashMap<String, String>());
	}
}
