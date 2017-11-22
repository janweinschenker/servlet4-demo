package de.holisticon.servlet4demo;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GreetingTest {

  @Test
  public void testToString() {
    Greeting sut = new Greeting(42, "Test");

    assertEquals("Greetings, Test (42)", sut.toString());
  }
}
