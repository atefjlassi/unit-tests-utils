package com.luv2code.springmvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private StudentAndGradeService studentService;
  @Autowired
  private StudentDao studentDao;

  @BeforeEach
  public void setupDatabase() {
    jdbcTemplate.execute("insert into student (id, firstname, lastname, email_address) "
      + "values (1, 'Eric', 'Roby', 'eric.roby@gmail.com')");
  }

  @Test
  public void createStudentService() {
    studentService.createStudent("Chad", "Darby", "chad.darby@gmail.com");
    Optional<CollegeStudent> student = studentDao.findByEmailAddress("chad.darby@gmail.com");
    assertEquals("chad.darby@gmail.com", student.get().getEmailAddress(), "find by email address");
  }

  @Test
  public void isStudentNullCheck() {
    assertTrue(studentService.checkIfStudentIsNull(1));
    assertFalse(studentService.checkIfStudentIsNull(0));
  }

  @Test
  public void deleteStudentService() {
    Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);
    assertTrue(deletedCollegeStudent.isPresent(), "Return true");
    studentService.deleteStudent(1);
    deletedCollegeStudent = studentDao.findById(1);
    assertFalse(deletedCollegeStudent.isPresent(), "Return False");
  }

  @Sql("/insertData.sql")
  @Test
  public void getGradebookService() {
    Iterable<CollegeStudent> iterableCollegeStudents =  studentService.getGradeBook();
    List<CollegeStudent> collegeStudents = new ArrayList<>();
    iterableCollegeStudents.forEach(collegeStudent -> collegeStudents.add(collegeStudent));
    assertEquals(5, collegeStudents.size());
    assertNotEquals(1, collegeStudents.size());
  }

  @AfterEach
  public void setupAfterTransaction() {
    jdbcTemplate.execute("DELETE FROM student");
  }
}
