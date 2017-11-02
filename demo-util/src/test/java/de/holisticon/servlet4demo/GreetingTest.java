package de.holisticon.servlet4demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreetingTest {

  private Greeting sut;

  @Test
  public void testToString(){
    sut = new Greeting(42, "Test");

    assertEquals("Greetings, Test (42)", sut.toString());
  }
}
