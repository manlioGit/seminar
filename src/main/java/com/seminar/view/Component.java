package com.seminar.view;

import java.util.ArrayList;
import java.util.Collection;

public class Component {

	private final Collection<String> _messages;
	private final String _userInput;
	private final TYPE _type;

	public Component(TYPE type, Collection<String> messages, String userinput) {
		_type = type;
		_messages = messages;
		_userInput = userinput;
	}
	
	public Component(TYPE type, String userinput) {
		this(type, new ArrayList<String>(), userinput);
	}
	
	public Component(String userinput) {
		this(TYPE.SUCCESS, new ArrayList<String>(), userinput);
	}
	
	public Collection<String> messages() {
		return _messages;
	}

	public String userInput() {
		return _userInput;
	}

	public TYPE type() {
		return _type;
	}
	
	public String value(){
		return _type._name;
	}

	public String state() {
		return _type._state + " has-feedback";
	}
	
	public String icon(){
		return _type._icon;
	}
	
	public enum TYPE{
		SUCCESS("(success)", "has-success", "glyphicon-ok"),
		WARNING("(warning)", "has-warning", "glyphicon-warning-sign"),
		ERROR("(error)", "has-error", "glyphicon-remove");
		
		private final String _name;
		private final String _state;
		private final String _icon;
		
		TYPE(String name, String state, String icon){
			_name = name;
			_state = state;
			_icon = icon;
		}
	}
}
