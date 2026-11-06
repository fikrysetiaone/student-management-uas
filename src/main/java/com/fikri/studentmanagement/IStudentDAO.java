package com.fikri.studentmanagement;

import java.sql.SQLException;
import java.util.List;

public interface IStudentDAO {
    List<Student> getAllStudents() throws SQLException;
    void addStudent(Student student) throws SQLException;
    void updateStudent(Student student) throws SQLException;
    void deleteStudent(int studentId) throws SQLException;
}
