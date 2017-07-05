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
import com.seminar.controller.ControllerUtil;
import com.seminar.model.EntityModel;
import com.seminar.model.entity.Course;
import com.seminar.view.FeedBack;
import com.seminar.view.Form;
import com.seminar.view.Html;
import com.seminar.view.Layout;
import com.seminar.view.ResponseWrapper;
import com.seminar.view.ViewUtil;

public class CreateCourse implements Controller {

	public static final Route ROUTE = new Route("/course/create/?");
	
	@Override
	public boolean handles(String url) {
		return ROUTE.matches(url);
	}

	@Override
	public void execute(final Context context) throws Exception {
		
		FeedBack feedBack = new FeedBack();
		if (context.post()) {
			EntityModel entity = new EntityModel(Course.class, Course.rules(), context.requestMap());
			if (new ControllerUtil().saveCourse(context, entity, AllCourse.ROUTE)) {
				return;
			}  else {
				feedBack = new ViewUtil().feedback(entity, context);
			}
		}
		Iterable<String> components = asList(NAME, START, LOCATION, TOTAL_SEATS, ID);
		Html form = new Form(feedBack, CreateCourse.ROUTE, components, Course.ID);
		new ResponseWrapper(context.response()).render(new Layout("create course", form));
	}
}
