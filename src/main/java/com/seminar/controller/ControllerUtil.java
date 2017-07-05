package com.seminar.controller;

import java.io.IOException;

import com.Context;
import com.Route;
import com.seminar.model.EntityModel;
import com.seminar.model.entity.Course;
import com.seminar.model.entity.Student;
import com.seminar.model.mapper.CourseMapper;
import com.seminar.model.mapper.StudentMapper;

public class ControllerUtil {
	
	public boolean save(Context context, EntityModel entityModel, Route redirect) throws IOException {
		if (entityModel.isValid()) {
			Student course = entityModel.create();
			new StudentMapper(context.connection()).save(course);

			context.response().sendRedirect(redirect.toString());
			return true;
		}
		return false;
	}

	public boolean saveCourse(Context context, EntityModel entityModel, Route route) throws IOException {
		if (entityModel.isValid()) {
			Course course = entityModel.create();
			new CourseMapper(context.connection()).save(course);

			context.response().sendRedirect(route.toString());
			return true;
		}
		return false;
	}
}
