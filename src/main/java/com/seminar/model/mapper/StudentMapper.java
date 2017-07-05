package com.seminar.model.mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.seminar.model.entity.Student;

public class StudentMapper {

	private final Connection _connection;

	public StudentMapper(Connection connection) {
		_connection = connection;
	}

	public Iterable<Student> findAll() {
		try {
			PreparedStatement preparedStatement = _connection.prepareStatement("select * from student");
			ResultSet rs = preparedStatement.executeQuery();
			List<Student> students = new ArrayList<Student>();
			while(rs.next()){
				students.add(
						new Student(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3)
						)
					);
			}
			preparedStatement.close();
			rs.close();
			return students;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void save(Student student) {
		if(student.getId() == null){
			insert(student);
		} else {
			update(student);
		}
	}

	public void update(Student student){
		try {
			PreparedStatement ps = _connection.prepareStatement("update student set firstName = ?,lastName = ?  where id = ?");
			ps.setObject(1, student.getFirstName());
			ps.setObject(2, student.getLastName());
			
			ps.setObject(3, student.getId());
		
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void insert(Student student){
		try {
			PreparedStatement ps = _connection.prepareStatement("insert into student (firstName, lastName ) values (?,?)");
			ps.setObject(1, student.getFirstName());
			ps.setObject(2, student.getLastName());
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String studentId) {
		try {
			PreparedStatement ps = _connection.prepareStatement("delete from student where id = ?");
			ps.setObject(1, studentId);
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Student findById(String studentId) {
		try {
			PreparedStatement ps = _connection.prepareStatement("select * from student where id = ?");
			ps.setObject(1, studentId);
			
			ResultSet rs = ps.executeQuery();
			List<Student> students = new ArrayList<Student>();
			while(rs.next()){
				students.add(
						new Student(
							rs.getInt(1),
							rs.getString(2),
							rs.getString(3)
						)
					);
			}
			ps.close();
			rs.close();
			return students.get(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
