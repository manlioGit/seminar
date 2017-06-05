package com.seminar.view;

import static com.github.manliogit.javatags.lang.HtmlHelper.attr;
import static com.github.manliogit.javatags.lang.HtmlHelper.div;
import static com.github.manliogit.javatags.lang.HtmlHelper.form;
import static com.github.manliogit.javatags.lang.HtmlHelper.group;
import static com.github.manliogit.javatags.lang.HtmlHelper.input;
import static com.github.manliogit.javatags.lang.HtmlHelper.label;
import static com.github.manliogit.javatags.lang.HtmlHelper.textarea;
import static com.seminar.model.entity.Course.ID;
import static com.seminar.model.entity.Course.LOCATION;
import static com.seminar.model.entity.Course.NAME;
import static com.seminar.model.entity.Course.START;
import static com.seminar.model.entity.Course.TOTAL_SEATS;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.capitalize;

import java.util.ArrayList;
import java.util.List;

import com.Route;
import com.github.manliogit.javatags.element.Element;
import com.github.manliogit.javatags.element.attribute.Attribute;
import com.seminar.controller.course.Create;

public class CourseForm implements Html{

	private final FeedBack _feedBack;
	private final Route _action;
	private final List<String> _hidden;
	
	public CourseForm() {
		this(new FeedBack(), Create.ROUTE);
	}
	
	public CourseForm(FeedBack feedBack, Route action, String...hidden){
		_feedBack = feedBack;
		_action = action;
		_hidden = asList(hidden);
	}
	
	@Override
	public Element build() {
		
		List<Element> input = new ArrayList<Element>();
		for (String component: asList(NAME, START, LOCATION, TOTAL_SEATS, ID)) {
			input.add(
					div(atttibuteFor(component) ,                                                                                                      
						label(attr("class -> col-sm-2 control-label").add("for", component), capitalize(component)),                                                           
						div(attr(" class  -> col-sm-10"),                                                                                                   
							input(attr("class -> form-control", "type -> text").
									add(_feedBack.value(component)).
									add("name", component).
									add("id", component) 
								),
							_feedBack.message(component)
						)                                                                                                                   
					)
				);
		}
		
		return 
			form(attr("class -> form-horizontal", "method -> post", "action -> " + _action),                                                       
				group(input),
				div(attr(" class  -> form-group"),                                                                                                      
					label(attr("for -> description", "class -> col-sm-2 control-label"), "Description"),                                                           
					div(attr(" class  -> col-sm-10"),
						textarea(attr("class -> form-control", "id -> description", "name -> description", "placeholder -> description"), _feedBack.text("description"))
					)                                                                                                                    
				),
				div(attr(" class  -> form-group"),                                                                                                      
					div(attr(" class  -> col-sm-10 col-sm-offset-2"),                                                                                   
						input(attr("id -> submit", "name -> submit", "type -> submit",  "value -> Send", "class -> btn btn-primary"))                                  
					)                                                                                                                    
				)                                                                                                                        
			);                                                                                                                           
	}
	
	private Attribute atttibuteFor(String component){
		Attribute attribute = attr("class -> form-group").add(_feedBack.state(component));
		if(_hidden.contains(component)){
			attribute.add(attr("hidden -> hidden"));
		}
		return attribute;
	}
}
