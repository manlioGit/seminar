package com;

import static java.util.Arrays.asList;

import java.util.List;

import com.seminar.controller.Controller;
import com.seminar.controller.course.CourseController;
import com.seminar.controller.student.StudentController;

public class SeminarFactory {

	public List<Controller> create(){
		return asList(
					new CourseController(), 
					new StudentController()
				);
	}
}
