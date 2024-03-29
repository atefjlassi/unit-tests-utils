package com.luv2code.springmvc.service;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentAndGradeService {

  @Autowired
  private StudentDao studentDao;

  public void createStudent(String firstName, String lastName, String email) {
    CollegeStudent student = new CollegeStudent(firstName, lastName, email);
    student.setId(0);
    studentDao.save(student);
  }

  public boolean checkIfStudentIsNull(int studentId) {
    Optional<CollegeStudent> student = studentDao.findById(studentId);
    return student.isPresent();
  }

  public void deleteStudent(int studentId) {
    if (checkIfStudentIsNull(studentId)) {
      studentDao.deleteById(studentId);
    }
  }

  public Iterable<CollegeStudent> getGradeBook() {
    return studentDao.findAll();
  }
}
