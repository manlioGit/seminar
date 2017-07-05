package com.seminar.view;

import static com.github.manliogit.javatags.lang.HtmlHelper.a;
import static com.github.manliogit.javatags.lang.HtmlHelper.attr;
import static com.github.manliogit.javatags.lang.HtmlHelper.td;
import static com.github.manliogit.javatags.lang.HtmlHelper.text;
import static com.github.manliogit.javatags.lang.HtmlHelper.th;
import static com.github.manliogit.javatags.lang.HtmlHelper.tr;

import com.github.manliogit.javatags.element.Element;
import com.seminar.model.entity.Entity;
import com.seminar.model.entity.Student;

public class StudentRowFactory implements RowFactory {

	@Override
	public Element row(Entity e) {
		Student s = (Student)e; 
		return tr(
					td(text(s.getId().toString())),
					th(attr("scope -> row"), a(attr("href -> /student/" + s.getId()),text(s.getFirstName()))),
					td(text(s.getLastName())),
					td(a(attr("href -> " + "/student/delete"+ "/" + s.getId()),"delete"))
				);
	}
}
