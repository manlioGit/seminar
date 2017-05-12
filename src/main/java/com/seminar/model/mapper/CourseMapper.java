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
	private static final String List = null;
	private final Connection _connection;

	public CourseMapper(Connection connection) {
		_connection = connection;
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

	public Iterable<Course> findAll() {
		try {
			PreparedStatement preparedStatement = _connection.prepareStatement("select * from Course");
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
}