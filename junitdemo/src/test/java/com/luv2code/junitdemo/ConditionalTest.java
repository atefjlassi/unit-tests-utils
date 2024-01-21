package com.luv2code.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

public class ConditionalTest {

  @Test
  @Disabled("don't run until fix JIRA #123 is resolved")
  void basicTest() {
    // execute method and perform asserts
  }

  @Test
  @EnabledOnOs(OS.WINDOWS)
  void testForWindowsOnly() {
    // execute method and perform asserts
  }

  @Test
  @EnabledOnOs(OS.MAC)
  void testForMacOnly() {
    // execute method and perform asserts
  }

  @Test
  @EnabledOnOs({OS.MAC, OS.WINDOWS})
  void testForMacAndWindowsOnly() {
    // execute method and perform asserts
  }

  @Test
  @EnabledOnJre(JRE.JAVA_11)
  void testOnlyForJreVersion11() {
    // execute method and perform asserts
  }
  @Test
  @EnabledOnJre(JRE.JAVA_13)
  void testOnlyForJreVersion13() {
    // execute method and perform asserts
  }

  @Test
  @EnabledForJreRange(min = JRE.JAVA_11, max = JRE.JAVA_17)
  void testOnlyForJreVersionRange11_17() {
    // execute method and perform asserts
  }

  @Test
  @EnabledForJreRange(min = JRE.JAVA_11)
  void testOnlyForJreVersionRange11Min() {
    // execute method and perform asserts
  }

  @Test
  @EnabledIfEnvironmentVariable(named = "LUV2CODE_ENV", matches = "DEV")
  void testOnlyForDevEnvironment() {
    // execute method and perform asserts
  }

  @Test
  @EnabledIfSystemProperty(named = "LUV2CODE_SYS_PROP", matches = "CI_CD_DEPLOY")
  void testOnlyForSystemProperty() {
    // execute method and perform asserts
  }

}
