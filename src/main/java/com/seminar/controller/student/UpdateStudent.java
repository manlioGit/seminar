package com.seminar.controller.student;

import java.util.HashMap;
import java.util.Map;

import com.Context;
import com.seminar.controller.Controller;
import com.seminar.controller.ControllerUtil;
import com.seminar.model.EntityModel;
import com.seminar.model.Meta;
import com.seminar.model.entity.Student;
import com.seminar.model.mapper.StudentMapper;
import com.seminar.view.Component;
import com.seminar.view.FeedBack;
import com.seminar.view.Form;
import com.seminar.view.Html;
import com.seminar.view.Layout;
import com.seminar.view.ResponseWrapper;
import com.seminar.view.ViewUtil;

public class UpdateStudent implements Controller {

	@Override
	public boolean handles(String url) {
		return StudentController.UPDATE.matches(url);
	}

	@Override
	public void execute(Context context) throws Exception {
		FeedBack feedBack = new FeedBack();
		if (context.post()) {
			EntityModel entityModel = new EntityModel(Student.class, Student.rules(), context.requestMap());
			if (new ControllerUtil().save(context, entityModel, StudentController.ALL)) {
				return;
			} else {
				feedBack = new ViewUtil().feedback(entityModel, context);
			}
		} else {
			String studentId = context.requestUri().replaceAll("\\D", "");

			final Student student = new StudentMapper(context.connection()).findById(studentId);
			Map<String, Component> map = new HashMap<String, Component>() {
				{
					put("id", new Component(student.getId().toString()));
					put("firstName", new Component(student.getFirstName()));
					put("lastName", new Component(student.getLastName()));
				}
			};
			feedBack = new FeedBack(map);
		}
		Iterable<String> components = Meta.signatureOf(Student.class);
		Html form = new Form(feedBack, StudentController.UPDATE, components, false, "id");
		
		new ResponseWrapper(context.response()).render(new Layout("update student", form));
	}
}
