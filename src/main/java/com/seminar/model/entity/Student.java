package com.seminar.model.entity;

import static java.util.Arrays.asList;

import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import com.seminar.model.rule.MaxLength;
import com.seminar.model.rule.NotEmpty;
import com.seminar.model.rule.Rule;

public class Student implements Entity {

	private final Integer _id;
	private final String _firstName;
	private final String _lastName;

	public Student(Integer id, String firstName, String lastName){
		_id = id;
		_firstName = firstName;
		_lastName = lastName;
	}
	
	public Student(Map<String, String> params) {
		this(
			params.get("id") == null ? null : Integer.parseInt(params.get("id")),
			params.get("firstName"),
			params.get("lastName")
		 );
	}
	
	public Student(String firstName, String lastName){
		this(0, firstName, lastName);
	}
	
	public String getFullName(){
		return getFirstName() + " " + getLastName();
	}

	public String getFirstName() {
		return _firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public Integer getId() {
		return _id;
	}
	
	public static MultiValuedMap<String, Rule> rules(){
		 return new ArrayListValuedHashMap<String, Rule>(){{
			putAll("firstName",   asList(new NotEmpty(), new MaxLength(15)));
			putAll("lastName", 	  asList(new NotEmpty(), new MaxLength(20)));
		}};
	}
}
