package com.seminar.controller.course;

import com.Context;
import com.Route;
import com.seminar.controller.Controller;
import com.seminar.model.mapper.CourseMapper;

public class DeleteCourse implements Controller {

	public static final Route ROUTE = new Route("/course/delete/[0-9]+");
	
	@Override
	public boolean handles(String url) {
		return ROUTE.matches(url);
	}

	@Override
	public void execute(Context context) throws Exception {
		String courseId = context.requestUri().replaceAll("\\D", "");
		
		new CourseMapper(context.connection()).delete(courseId);
		
		context.response().sendRedirect(AllCourse.ROUTE.toString());
		return;
	}
}
