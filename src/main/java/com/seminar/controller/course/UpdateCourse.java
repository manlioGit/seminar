package com.seminar.controller.course;

import static com.seminar.model.entity.Course.ID;
import static com.seminar.model.entity.Course.LOCATION;
import static com.seminar.model.entity.Course.NAME;
import static com.seminar.model.entity.Course.START;
import static com.seminar.model.entity.Course.TOTAL_SEATS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.Context;
import com.Route;
import com.seminar.controller.Controller;
import com.seminar.controller.ControllerUtil;
import com.seminar.model.EntityModel;
import com.seminar.model.entity.Course;
import com.seminar.model.mapper.CourseMapper;
import com.seminar.view.Component;
import com.seminar.view.FeedBack;
import com.seminar.view.Form;
import com.seminar.view.Html;
import com.seminar.view.Layout;
import com.seminar.view.ResponseWrapper;
import com.seminar.view.ViewUtil;

public class UpdateCourse implements Controller {

	public static final Route ROUTE = new Route("/course/update", "/course/[0-9]+");

	@Override
	public boolean handles(String url) {
		return ROUTE.matches(url);
	}

	@Override
	public void execute(Context context) throws Exception {
		FeedBack feedBack = new FeedBack();
		if (context.post()) {
			EntityModel entityModel = new EntityModel(Course.class, Course.rules(), context.requestMap());
			if (new ControllerUtil().saveCourse(context, entityModel, AllCourse.ROUTE)) {
				return;
			} else {
				feedBack = new ViewUtil().feedback(entityModel, context);
			}
		} else {
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
			feedBack = new FeedBack(map);
		}
		List<String> components = Arrays.asList(NAME, START, LOCATION, TOTAL_SEATS, ID);
		Html form = new Form(feedBack, UpdateCourse.ROUTE, components, "id");
		
		new ResponseWrapper(context.response()).render(new Layout("update course", form));
	}
}
