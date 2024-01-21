package com.luv2code.springmvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

@TestPropertySource("/application.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class GradeBookControllerTest {

  @Autowired
  private JdbcTemplate jdbc;
  @Autowired
  private MockMvc mockMvc;
  @Mock
  private StudentAndGradeService studentCreateServiceMock;

  /**
   * why we declared static ? because @BeforeAll methods must be declared static also must be public
   * and return void
   */
  public static MockHttpServletRequest request;
  @Autowired
  private StudentDao studentDao;

  @BeforeAll
  public static void setup() {
    request = new MockHttpServletRequest();
    request.setParameter("firstname", "Chad");
    request.setParameter("lastname", "Darby");
    request.setParameter("emailAddress", "chad.darby@gmail.com");
  }

  @BeforeEach
  public void setupDatabase() {
    jdbc.execute("insert into student (id, firstname, lastname, email_address) "
      + "values (1, 'Eric', 'Roby', 'eric.roby@gmail.com')");
  }

  @Test
  @DisplayName("**** execute getStudentsHttpRequest() ****")
  public void getStudentsHttpRequest() throws Exception {
    GradebookCollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Roby",
      "eric.roby@gmail.com");
    GradebookCollegeStudent studentTwo = new GradebookCollegeStudent("Chad", "Darby",
      "chad.darby@gmail.com");

    ArrayList<CollegeStudent> collegeStudentList = new ArrayList<>(
      Arrays.asList(studentOne, studentTwo));
    when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudentList);

    assertIterableEquals(collegeStudentList, studentCreateServiceMock.getGradeBook());
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
      .andExpect(status().isOk()).andReturn();

    ModelAndView mav = mvcResult.getModelAndView();
    assertViewName(mav, "index");

  }

  @Test
  @DisplayName("**** create student ****")
  public void createStudentHttpRequest() throws Exception {

    CollegeStudent student = new CollegeStudent("Eric", "Roby", "eric_roby@gmail.com");
    ArrayList<CollegeStudent> collegeStudents = new ArrayList<>(Arrays.asList(student));
    when(studentCreateServiceMock.getGradeBook()).thenReturn(collegeStudents);

    assertIterableEquals(collegeStudents, studentCreateServiceMock.getGradeBook());

    MvcResult mvcResult = this.mockMvc.perform(post("/post").contentType(MediaType.APPLICATION_JSON)
        .param("firstname", request.getParameterValues("firstname"))
        .param("lastname", request.getParameterValues("lastname"))
        .param("emailAddress", request.getParameterValues("emailAddress"))).andExpect(status().isOk())
      .andReturn();

    ModelAndView mav = mvcResult.getModelAndView();
    assertViewName(mav, "index");
    Optional<CollegeStudent> verifyIfStudentExist = studentDao.findByEmailAddress(
      "chad.darby@gmail.com");
    assertNotNull(verifyIfStudentExist.get(), "should not be true");
  }

  @Test
  @DisplayName("*** delete student ***")
  public void deleteStudentHttpRequest() throws Exception {

    assertTrue(studentDao.findById(1).isPresent());
    MvcResult mvcResult = this.mockMvc.perform(get("/delete/student/{id}", 1))
      .andExpect(status().isOk()).andReturn();
    ModelAndView mav = mvcResult.getModelAndView();
    assertViewName(mav, "index");

    assertFalse(studentDao.findById(1).isPresent());
  }


  @Test
  public void deleteStudentHttpRequestErrorPage() throws Exception {
    MvcResult mvcResult = mockMvc.perform(get("/delete/student/{id}", 0))
      .andExpect(status().isOk()).andReturn();
    ModelAndView mav = mvcResult.getModelAndView();
    assertViewName(mav, "error");
  }

  @AfterEach
  public void setupAfterTransaction() {
    jdbc.execute("DELETE FROM student");
  }

}
