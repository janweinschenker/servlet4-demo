package de.holisticon.servlet4demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GreetingTest {

  @Test
  public void testToString() {
    Greeting sut = new Greeting(42, "Test");

    assertEquals("Greetings, Test (42)", sut.toString());
  }
}
