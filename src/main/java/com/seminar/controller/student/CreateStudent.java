package com.seminar.controller.student;

import static java.util.Arrays.asList;

import com.Context;
import com.seminar.controller.Controller;
import com.seminar.controller.ControllerUtil;
import com.seminar.model.EntityModel;
import com.seminar.model.entity.Student;
import com.seminar.view.FeedBack;
import com.seminar.view.Form;
import com.seminar.view.Html;
import com.seminar.view.Layout;
import com.seminar.view.ResponseWrapper;
import com.seminar.view.ViewUtil;

public class CreateStudent implements Controller {

	@Override
	public boolean handles(String url) {
		return StudentController.CREATE.matches(url);
	}

	@Override
	public void execute(Context context) throws Exception {
		Iterable<String> components = asList("firstName", "lastName");
		FeedBack feedBack = new FeedBack();
		if (context.post()) {
			EntityModel entityModel = new EntityModel(Student.class, Student.rules(), context.requestMap());
			if (new ControllerUtil().save(context, entityModel, StudentController.ALL)) {
				return;
			} else {
				feedBack = new ViewUtil().feedback(entityModel, context);
			}
		}
		
		Html form = new Form(feedBack, StudentController.CREATE, components, false, "id"); 
		new ResponseWrapper(context.response()).render(new Layout("create student", form));
	}
}
