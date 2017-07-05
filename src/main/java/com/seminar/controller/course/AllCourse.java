package com.seminar.controller.course;

import static com.seminar.model.entity.Course.ID;
import static com.seminar.model.entity.Course.LOCATION;
import static com.seminar.model.entity.Course.NAME;
import static com.seminar.model.entity.Course.START;
import static com.seminar.model.entity.Course.TOTAL_SEATS;
import static java.util.Arrays.asList;

import com.Context;
import com.Route;
import com.seminar.controller.Controller;
import com.seminar.model.entity.Course;
import com.seminar.model.mapper.CourseMapper;
import com.seminar.view.CourseComponentFactory;
import com.seminar.view.Layout;
import com.seminar.view.ResponseWrapper;
import com.seminar.view.Table;

public class AllCourse implements Controller {

	public final static Route ROUTE = new Route("/", "/course/?");
	
	@Override
	public boolean handles(String url) {
		return ROUTE.matches(url);
	}
	
	@Override
	public void execute(Context context) throws Exception {
		Iterable<Course> courses = new CourseMapper(context.connection()).findAll();
		Iterable<String> header = asList(ID, NAME, LOCATION, TOTAL_SEATS, START, "action");
		
		new ResponseWrapper(context.response()).render(new Layout("courses", new Table(courses, header, new CourseComponentFactory())));
	}
}
