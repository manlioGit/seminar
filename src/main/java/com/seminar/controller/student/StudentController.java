package com.seminar.controller.student;

import static java.util.Arrays.asList;

import java.util.List;

import com.Context;
import com.Route;
import com.seminar.controller.Controller;

public class StudentController implements Controller {

	public static final Route ALL = new Route("/student/?");
	public static final Route CREATE = new Route("/student/create/?");
	public static final Route UPDATE = new Route("/student/update", "/student/[0-9]+");
	public static final Route DELETE = new Route("/student/delete/[0-9]+");

	@Override
	public boolean handles(String url) {
		for (Route route : asList(ALL, CREATE, UPDATE, DELETE)) {
			if (route.matches(url)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(Context context) throws Exception {
		context.response().setContentType("text/html;charset=UTF-8");
		List<Controller> actions = asList(new AllStudent(), new CreateStudent(), new UpdateStudent(), new DeleteStudent());
		
		for (Controller action : actions) {
			if(action.handles(context.requestUri())){
				action.execute(context);
			}
		}
	}
}
