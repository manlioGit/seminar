package com.seminar.model.entity;

import static com.seminar.model.rule.Number.OPERATOR.*;
import static java.util.Arrays.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import com.seminar.model.rule.MaxLength;
import com.seminar.model.rule.NotEmpty;
import com.seminar.model.rule.Number;
import com.seminar.model.rule.Rule;
import com.seminar.model.rule.TimeFormat;

public class Course implements Entity {

	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String LOCATION = "location";
	public static final String TOTAL_SEATS = "totalSeats";
	public static final String START = "start";
	
	private final Integer _id;
	private final String _name;
	private final String _description;
	private final String _location;
	private final Integer _totalSeats;
	private final Time _start;

	private final List<Student> _students;
	
	public Course(Integer id, String name, String description, String location, Integer totalSeats, Time start) {
		_id = id ;
		_name = name;
		_description = description;
		_location = location;
		_totalSeats = totalSeats;
		_start = start; 
		_students = new ArrayList<>();
	}
	
	public Course(Map<String, String> params) {
		this(
			params.get(ID).equals("") ? null : Integer.parseInt(params.get(ID)),
			params.get(NAME), 
			params.get(DESCRIPTION), 
			params.get(LOCATION), 
			Integer.valueOf(params.get(TOTAL_SEATS)), 
			new Time(params.get(START))
		 );
	}

	public static MultiValuedMap<String, Rule> rules(){
		 return new ArrayListValuedHashMap<String, Rule>(){{
			putAll(NAME, 		  asList(new NotEmpty(), new MaxLength(15)));
			putAll(LOCATION, 	  asList(new NotEmpty(), new MaxLength(20)));
			putAll(TOTAL_SEATS,   asList(new Number(GREATER_THAN, 0), new Number(LESS_THAN, 100), new MaxLength(3)));
			put(START, 			  new TimeFormat(Time.FORMAT));
		}};
	}
	
	public String getName() {
		return _name;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public Integer getId() {
		return _id;
	}
	
	public String getLocation() {
		return _location;
	}
	
	public Integer getSeatsLeft(){
		return getTotalSeats() - _students.size();
	}
	
	public void enroll(Student student) {
		if(_students.size()  < getSeatsLeft()) {
			_students.add(student); 
		} else {
			throw new RuntimeException("Student: " + student.getFullName() + " can't be enrolled. Seats terminated");
		}
	}
	
	public Iterable<Student> getStudents() {
		return _students;
	}

	public Time getTime() {
		return _start;
	}

	public Integer getTotalSeats() {
		return _totalSeats;
	}
}
