package de.holisticon.servlet4demo.serverjetty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;

import org.eclipse.jetty.server.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.holisticon.servlet4demo.Greeting;

public class GreetingControllerTest {

  private GreetingController greetingController;
  private HttpServletRequest request;
  private PushBuilder pushBuilder;

  @BeforeEach
  public void setUp() {
    JettyServerPushFunction jettyServerPushFunction = mock(JettyServerPushFunction.class);
    this.greetingController = new GreetingController(jettyServerPushFunction);
    this.request = mock(HttpServletRequest.class);
    this.pushBuilder = mock(PushBuilder.class);
    Request jettyBaseRequest = mock(Request.class);
    org.eclipse.jetty.server.PushBuilder jettyPushBuilder = mock(org.eclipse.jetty.server.PushBuilder.class);

    when(pushBuilder.path(anyString())).thenReturn(this.pushBuilder);
    when(jettyPushBuilder.path(anyString())).thenReturn(jettyPushBuilder);
    when(jettyBaseRequest.getPushBuilder()).thenReturn(jettyPushBuilder);
  }

  @Test
  public void testGreeting() {
    when(request.newPushBuilder()).thenReturn(pushBuilder);
    Greeting greeting = this.greetingController.greeting(this.request, "JavaLandTest");
    assertNotNull(greeting);
    assertEquals("Hello, JavaLandTest!", greeting.getContent());
    verify(pushBuilder, times(1)).path(anyString());
  }

  @Test
  public void testGreetingNoPushBuilder() {
    when(request.newPushBuilder()).thenReturn(null);
    Greeting greeting = this.greetingController.greeting(this.request, "JavaLandTest");
    assertNotNull(greeting);
    assertEquals("Hello, JavaLandTest!", greeting.getContent());
    verify(pushBuilder, times(0)).path(anyString());
  }

  @Test
  public void testPushGreeting() {
    Greeting greeting = this.greetingController.pushGreeting("JavaLandTest");
    assertNotNull(greeting);
    assertEquals("Hello, JavaLandTest!", greeting.getContent());

  }
}
