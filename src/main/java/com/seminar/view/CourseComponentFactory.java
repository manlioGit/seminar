package com.seminar.view;

import static com.github.manliogit.javatags.lang.HtmlHelper.a;
import static com.github.manliogit.javatags.lang.HtmlHelper.attr;
import static com.github.manliogit.javatags.lang.HtmlHelper.td;
import static com.github.manliogit.javatags.lang.HtmlHelper.text;
import static com.github.manliogit.javatags.lang.HtmlHelper.th;
import static com.github.manliogit.javatags.lang.HtmlHelper.tr;

import com.github.manliogit.javatags.element.Element;
import com.seminar.controller.course.DeleteCourse;
import com.seminar.model.entity.Course;
import com.seminar.model.entity.Entity;

public class CourseComponentFactory implements RowFactory {

	@Override
	public Element row(Entity e) {
		Course c = (Course)e; 
		return tr(
				td(text(c.getId().toString())),
				th(attr("scope -> row"), a(attr("href -> /course/" + c.getId()),text(c.getName()))),
				td(text(c.getLocation())),
				td(text(c.getTotalSeats().toString())),
				td(text(c.getTime().toString())),
				td(a(attr("href -> " + DeleteCourse.ROUTE + "/" + c.getId()),"delete"))
			);
	}
}
