package com.seminar.controller.student;

import com.Context;
import com.seminar.controller.Controller;
import com.seminar.model.mapper.StudentMapper;

public class DeleteStudent implements Controller {

	@Override
	public boolean handles(String url) {
		return StudentController.DELETE.matches(url);
	}

	@Override
	public void execute(Context context) throws Exception {
		String studentId = context.requestUri().replaceAll("\\D", "");
		
		new StudentMapper(context.connection()).delete(studentId);

		context.response().sendRedirect(StudentController.ALL.toString());
	}
}
