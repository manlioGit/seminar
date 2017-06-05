package com.seminar.controller.course;

import static java.util.Arrays.asList;

import java.util.List;

import com.Context;
import com.Route;
import com.seminar.controller.Controller;

public class CourseController implements Controller {

	private static final Iterable<Route> REGISTERED = asList(
			AllCourse.ROUTE, Create.ROUTE, Update.ROUTE, Delete.ROUTE
		);
	
	@Override
	public boolean handles(String url) {
		for (Route registered : REGISTERED) {
			if(registered.matches(url)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void execute(Context context) throws Exception {

		context.response().setContentType("text/html;charset=UTF-8");

		List<Controller> actions = asList(new AllCourse(), new Create(), new Update(), new Delete());
		for (Controller action : actions) {
			if(action.handles(context.requestUri())){
				action.execute(context);
			}
		}
	}
}
