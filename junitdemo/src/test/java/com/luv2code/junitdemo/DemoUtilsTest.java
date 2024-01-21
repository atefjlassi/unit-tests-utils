package com.luv2code.junitdemo;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

//@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DemoUtilsTest {

  DemoUtils demoUtils;

  @BeforeEach
  void setUp() {
    demoUtils = new DemoUtils();
    System.out.println("@BeforeEach executes before the execution of each test method");
  }

  @AfterEach
  void tearDown() {

    demoUtils = null;
    System.out.println("@AfterEach executes after the execution of each test method");
    System.out.println();
  }

  @BeforeAll
  static void setupBeforeEachClass() {
    System.out.println(
      "@BeforeAll executes only once before all test methods execution in the class");
    System.out.println();
  }

  @AfterAll
  static void tearDownAfterAll() {
    System.out.println(
      "@AfterAll executes only once after all test methods execution in the class");
  }

  @Test
  @DisplayName("**** test equality of add() method ****")
  void testAddEqualsAndNotEquals() {
    System.out.println("Running test: testAddEqualsAndNotEquals");
    assertEquals(6, demoUtils.add(2, 4), "2 + 4 must be 6");
    assertNotEquals(8, demoUtils.add(2, 4));
  }


  @Test
  @Order(1)
  void testCheckNullAndNotNull() {
    System.out.println("Running test: testCheckNullAndNotNull");
    assertNull(demoUtils.checkNull(null), "Object should be null");
    assertNotNull(demoUtils.checkNull("Hola"), "Object should not be null");
  }

  @Test
  void testSameAndNotSame() {
    assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(),
      "Object should refer to same object");
    assertNotSame("luv2code", demoUtils.getAcademyDuplicate(),
      "Object should not refer to same object");
  }

  @Test
  void testIsGreaterThan() {
    assertTrue(demoUtils.isGreater(10, 5), "This should return true");
    assertFalse(demoUtils.isGreater(10, 15), "This should return false");
  }

  @Test
  void testArrayEquals() {
    String[] expectedArray = {"A", "B", "C"};
    assertArrayEquals(expectedArray, demoUtils.getFirstThreeLettersOfAlphabet(),
      "should be the same array");
  }

  @Test
  void testIterableEquals() {
    List<String> stringList = List.of("luv", "2", "code");
    assertIterableEquals(stringList, demoUtils.getAcademyInList(),
      "expected list should be the same");
  }

  @Test
  void testLinesMatch() {
    List<String> stringList = List.of("luv", "2", "code");
    assertLinesMatch(stringList, demoUtils.getAcademyInList(),
      "lines should match");
  }

  @Test
  void testThrowAndNotThrow() {
    assertThrows(Exception.class, () -> demoUtils.throwException(-9), "should throw an exception");
    assertDoesNotThrow(() -> demoUtils.throwException(9), "should not throw an exception");
  }

  @Test
  void testTimeout() {
    assertTimeout(Duration.ofSeconds(3),() -> demoUtils.checkTimeout(), "Method should execute within 3 seconds" );
  }

  @Test
  @Disabled("Don't run this test until fix bug")
  void testMultiply() {
    assertEquals(12,demoUtils.multiply(2,6));
    assertNotEquals(0,demoUtils.multiply(2,6));
  }
}