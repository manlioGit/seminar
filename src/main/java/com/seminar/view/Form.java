package com.seminar.view;

import static com.github.manliogit.javatags.lang.HtmlHelper.attr;
import static com.github.manliogit.javatags.lang.HtmlHelper.div;
import static com.github.manliogit.javatags.lang.HtmlHelper.form;
import static com.github.manliogit.javatags.lang.HtmlHelper.group;
import static com.github.manliogit.javatags.lang.HtmlHelper.input;
import static com.github.manliogit.javatags.lang.HtmlHelper.label;
import static com.github.manliogit.javatags.lang.HtmlHelper.textarea;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.capitalize;

import java.util.ArrayList;
import java.util.List;

import com.Route;
import com.github.manliogit.javatags.element.Element;
import com.github.manliogit.javatags.element.attribute.Attribute;

public class Form implements Html {

	private final FeedBack _feedBack;
	private final Route _action;
	private final List<String> _hidden;
	private final Iterable<String> _components;
	private final boolean _showDescription;
	
	public Form(FeedBack feedBack, Route action, Iterable<String> components, boolean showDescription, String...hidden){
		_feedBack = feedBack;
		_action = action;
		_components = components;
		_showDescription = showDescription;
		_hidden = asList(hidden);
	}
	
	public Form(FeedBack feedBack, Route action, Iterable<String> components, String...hidden){
		this(feedBack, action, components, true, hidden);
	}
	
	@Override
	public Element build() {
		
		List<Element> input = new ArrayList<Element>();
		for (String component: _components) {
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
				description(),
				div(attr(" class  -> form-group"),                                                                                                      
					div(attr(" class  -> col-sm-10 col-sm-offset-2"),                                                                                   
						input(attr("id -> submit", "name -> submit", "type -> submit",  "value -> Send", "class -> btn btn-primary"))                                  
					)                                                                                                                    
				)                                                                                                                        
			);                                                                                                                           
	}

	private Element description() {
		return _showDescription ? 
		 div(attr(" class  -> form-group"),                                                                                                      
			label(attr("for -> description", "class -> col-sm-2 control-label"), "Description"),                                                           
			div(attr(" class  -> col-sm-10"),
				textarea(attr("class -> form-control", "id -> description", "name -> description", "placeholder -> description"), _feedBack.text("description"))
			)                                                                                                                    
		)
		: div();
	}
	
	private Attribute atttibuteFor(String component){
		Attribute attribute = attr("class -> form-group").add(_feedBack.state(component));
		if(_hidden.contains(component)){
			attribute.add(attr("hidden -> hidden"));
		}
		return attribute;
	}
}
