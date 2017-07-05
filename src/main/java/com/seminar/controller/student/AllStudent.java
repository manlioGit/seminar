package com.seminar.controller.student;

import java.util.ArrayList;
import java.util.List;

import com.Context;
import com.seminar.controller.Controller;
import com.seminar.model.Meta;
import com.seminar.model.entity.Student;
import com.seminar.model.mapper.StudentMapper;
import com.seminar.view.Layout;
import com.seminar.view.ResponseWrapper;
import com.seminar.view.StudentRowFactory;
import com.seminar.view.Table;

public class AllStudent implements Controller {

	@Override
	public boolean handles(String url) {
		return StudentController.ALL.matches(url);
	}

	@Override
	public void execute(Context context) throws Exception {
		Iterable<Student> courses = new StudentMapper(context.connection()).findAll();
		List<String> header = new ArrayList<>(Meta.signatureOf(Student.class));
		header.add("action");
		
		Table table = new Table(courses, header, new StudentRowFactory());
		
		new ResponseWrapper(context.response()).render(new Layout("students", table));
	}
}
