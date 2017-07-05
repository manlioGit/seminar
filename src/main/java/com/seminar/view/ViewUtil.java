package com.seminar.view;

import java.util.HashMap;
import java.util.Map;

import com.Context;
import com.seminar.model.EntityModel;
import com.seminar.view.Component.TYPE;

public class ViewUtil {

	public FeedBack feedback(EntityModel entityModel, Context context) {
		Map<String, Component> errors = new HashMap<String, Component>();
		for (String component : entityModel.signature()) {
			errors.put(component, componentType(component, entityModel, context));
		}
		return new FeedBack(errors);
	}

	private Component componentType(String label, EntityModel entityModel, Context context) {
		return entityModel.isBrokenOn(label)
				? new Component(TYPE.ERROR, entityModel.validate().get(label), context.parameter(label))
				: new Component(TYPE.SUCCESS, context.parameter(label));
	}
}
