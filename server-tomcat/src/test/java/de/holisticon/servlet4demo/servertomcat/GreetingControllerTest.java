package de.holisticon.servlet4demo.servertomcat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.holisticon.servlet4demo.Greeting;

public class GreetingControllerTest {

  private GreetingController sut;

  private HttpServletRequest request;
  private PushBuilder pushBuilder;

  @BeforeEach
  public void setUp() {
    sut = new GreetingController();
    request = mock(HttpServletRequest.class);
    pushBuilder = mock(PushBuilder.class);

    when(pushBuilder.path(anyString())).thenReturn(pushBuilder);
    when(request.newPushBuilder()).thenReturn(pushBuilder);
  }


  @Test
  public void testGreeting() {
    Greeting test = sut.greeting(request, "test");
    assertEquals("Hello, test!", test.getContent());
    verify(pushBuilder, times(1)).push();
  }

  @Test
  public void testPush() {
    Greeting test = sut.pushGreeting("push");
    assertEquals("Hello, push!", test.getContent());
    verify(pushBuilder, times(0)).push();
  }
}
