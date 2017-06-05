package com.seminar.model.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.seminar.model.entity.Course;
import com.seminar.model.entity.Time;

public class CourseMapper {

	private final Connection _connection;

	public CourseMapper(Connection connection) {
		_connection = connection;
	}
	
	public void update(Course course){
		try {
			PreparedStatement ps = _connection.prepareStatement("update course set name = ?,description = ?,location = ?, totalSeats = ?,start = ? where id = ?");
			ps.setObject(1, course.getName());
			ps.setObject(2, course.getDescription());
			ps.setObject(3, course.getLocation());
			ps.setObject(4, course.getTotalSeats());
			ps.setObject(5, course.getTime());
			
			ps.setObject(6, course.getId());
		
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void insert(Course course){
		try {
			PreparedStatement ps = _connection.prepareStatement("insert into Course (name, description, location, totalSeats, start) values (?,?,?,?,?)");
			ps.setObject(1, course.getName());
			ps.setObject(2, course.getDescription());
			ps.setObject(3, course.getLocation());
			ps.setObject(4, course.getTotalSeats());
			ps.setObject(5, course.getTime());
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void save(Course course){
		if(course.getId() == null){
			insert(course);
		} else {
			update(course);
		}
	}

	public Iterable<Course> findAll() {
		try {
			PreparedStatement preparedStatement = _connection.prepareStatement("select * from course");
			ResultSet rs = preparedStatement.executeQuery();
			List<Course> courses = new ArrayList<Course>();
			while(rs.next()){
				courses.add(
						new Course(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getInt(5),
							new Time(rs.getString(6))
						)
					);
			}
			preparedStatement.close();
			rs.close();
			return courses;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Course findById(String courseId) {
		try {
			PreparedStatement ps = _connection.prepareStatement("select * from Course where id = ?");
			ps.setObject(1, courseId);
			
			ResultSet rs = ps.executeQuery();
			List<Course> courses = new ArrayList<Course>();
			while(rs.next()){
				courses.add(
						new Course(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3),
							rs.getString(4),
							rs.getInt(5),
							new Time(rs.getString(6))
						)
					);
			}
			ps.close();
			rs.close();
			return courses.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String courseId) {
		try {
			PreparedStatement ps = _connection.prepareStatement("delete from course where id = ?");
			ps.setObject(1, courseId);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
