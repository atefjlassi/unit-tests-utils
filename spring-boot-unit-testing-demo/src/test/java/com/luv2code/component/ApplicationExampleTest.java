package com.luv2code.component;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ApplicationExampleTest {

  private static int count = 0;

  @Value("${info.app.name}")
  private String appInfo;
  @Value("${info.app.description}")
  private String appDescription;
  @Value("${info.app.version}")
  private String appVersion;
  @Value("${info.school.name}")
  private String schoolName;

  @Autowired
  CollegeStudent student;

  @Autowired
  StudentGrades studentGrades;

  @Autowired
  ApplicationContext context;

  @BeforeEach
  public void beforeEach() {
    count++;
    System.out.println(
      "Testing: " + appInfo + " which is " + appDescription + " Version " + appVersion
        + " .Execution of test method " + count);

    student.setFirstname("Eric");
    student.setLastname("Roby");
    student.setEmailAddress("eric.robu@luv2code_school.com");
    studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
    student.setStudentGrades(studentGrades);
  }

  @Test
  @DisplayName("Add grade results for student grades")
  void addGradeResultsForStudentGrades() {
    assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(
      student.getStudentGrades().getMathGradeResults()));
  }

  @Test
  @DisplayName("Add grade results for student grades not equal")
  void addGradeResultsForStudentGradesAssertNotEquals() {
    assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
      student.getStudentGrades().getMathGradeResults()));
  }

  @Test
  @DisplayName("Is grade greater")
  void isGradeGreaterStudentGrades() {
    assertTrue(studentGrades.isGradeGreater(90, 75), "failure - should be true");
  }

  @Test
  @DisplayName("Is grade greater false")
  public void isGradeGreaterStudentGradesAssertFalse() {
    assertFalse(studentGrades.isGradeGreater(90, 92), "failure - should be false");
  }

  @Test
  @DisplayName("Check Null for student grades")
  public void checkNullForStudentGrades() {
    assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()),
      "Object should not be null");
  }

  @DisplayName("Create student withoud grade init")
  @Test
  public void createStudentWithoudGradesInit() {
    CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
    studentTwo.setFirstname("Chad");
    studentTwo.setLastname("Darby");
    studentTwo.setEmailAddress("chad.darby@luv2code_school.com");
    assertNotNull(studentTwo.getFirstname());
    assertNotNull(studentTwo.getLastname());
    assertNotNull(studentTwo.getEmailAddress());
    assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
  }

  @DisplayName("Verify students are prototypes")
  @Test
  public void verifyStudentsArePrototypes() {
    CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
    assertNotSame(studentTwo, student);
  }

  @DisplayName("Find Grade Point Average")
  @Test
  public void findGradePointAverage() {
    assertAll("Testing all assertEquals",
      () -> assertEquals(353.25, studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults())),
      () -> assertEquals(88.31, studentGrades.findGradePointAverage(student.getStudentGrades().getMathGradeResults())));
  }
}
