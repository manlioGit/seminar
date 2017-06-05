package com.seminar.controller.course;

import static com.seminar.model.Meta.signatureOf;

import java.util.HashMap;
import java.util.Map;

import com.Context;
import com.Route;
import com.seminar.controller.Controller;
import com.seminar.model.EntityModel;
import com.seminar.model.entity.Course;
import com.seminar.model.mapper.CourseMapper;
import com.seminar.view.Component;
import com.seminar.view.Component.TYPE;
import com.seminar.view.CourseForm;
import com.seminar.view.FeedBack;
import com.seminar.view.Layout;
import com.seminar.view.ResponseWrapper;

public class Create implements Controller {

	public static final Route ROUTE = new Route("/course/create/?", "/course/update");

	@Override
	public boolean handles(String url) {
		return ROUTE.matches(url);
	}
	
	@Override
	public void execute(final Context context) throws Exception {
			CourseForm courseForm = new CourseForm(new FeedBack(), Create.ROUTE, Course.ID);
			if(context.post()){

				EntityModel entity = new EntityModel(Course.rules(), context.requestMap());
				if(entity.isValid()){
					Course course = entity.<Course>create(Course.class); 
					new CourseMapper(context.connection()).save(course);
					
					context.response().sendRedirect(AllCourse.ROUTE.toString());
					return;
				} else {
					Map<String, Component> errors = new HashMap<String, Component>();
					for (String component : signatureOf(Course.class)) {
						errors.put(component, componentType(component, entity, context));
					} 
					
					courseForm = new CourseForm(new FeedBack(errors), Create.ROUTE, Course.ID);
				}
			}
			
			Layout layout = new Layout("create course",  courseForm);
			new ResponseWrapper(context.response()).render(layout);
	}
	
	private Component componentType(String label, EntityModel course, Context context){
		return course.isBrokenOn(label) 
				? new Component(TYPE.ERROR, course.validate().get(label), context.parameter(label))
				:  new Component(TYPE.SUCCESS, context.parameter(label));
		
	}
}
