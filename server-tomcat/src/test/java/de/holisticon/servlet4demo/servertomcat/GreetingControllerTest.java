package de.holisticon.servlet4demo.servertomcat;

import de.holisticon.servlet4demo.Greeting;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class GreetingControllerTest {

  private GreetingController sut;

  private HttpServletRequest request;
  private PushBuilder pushBuilder;

  @Before
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
