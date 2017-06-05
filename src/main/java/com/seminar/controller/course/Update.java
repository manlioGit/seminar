package com.seminar.controller.course;

import java.util.HashMap;
import java.util.Map;

import com.Context;
import com.Route;
import com.seminar.controller.Controller;
import com.seminar.model.entity.Course;
import com.seminar.model.mapper.CourseMapper;
import com.seminar.view.Component;
import com.seminar.view.CourseForm;
import com.seminar.view.FeedBack;
import com.seminar.view.Layout;
import com.seminar.view.ResponseWrapper;

public class Update implements Controller {

	public static final Route ROUTE = new Route("/course/[0-9]+");

	@Override
	public boolean handles(String url) {
		return ROUTE.matches(url);
	}

	@Override
	public void execute(Context context) throws Exception {
		String courseId = context.requestUri().replaceAll("\\D", "");
		
		final Course course = new CourseMapper(context.connection()).findById(courseId);
		Map<String, Component> map = new HashMap<String, Component>(){{
			put(Course.ID , new Component(course.getId().toString()));
			put(Course.NAME , new Component(course.getName()));
			put(Course.DESCRIPTION , new Component(course.getDescription()));
			put(Course.LOCATION , new Component(course.getLocation()));
			put(Course.START , new Component(course.getTime().toString()));
			put(Course.TOTAL_SEATS , new Component(course.getTotalSeats().toString()));
		}};
		new ResponseWrapper(context.response()).render(new Layout("create course",  new CourseForm(new FeedBack(map), new Route("/course/update"), Course.ID)));
	}
}
