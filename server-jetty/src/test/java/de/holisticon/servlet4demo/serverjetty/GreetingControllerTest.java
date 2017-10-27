package de.holisticon.servlet4demo.serverjetty;

import de.holisticon.servlet4demo.Greeting;
import de.holisticon.servlet4demo.serverjetty.GreetingController;
import de.holisticon.servlet4demo.serverjetty.JettyServerPushFunction;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GreetingControllerTest {

  private GreetingController greetingController;
  private HttpServletRequest request;
  private PushBuilder pushBuilder;
  private org.eclipse.jetty.server.PushBuilder jettyPushBuilder;
  private Request jettyBaseRequest;
  private JettyServerPushFunction jettyServerPushFunction;

  @Before
  public void setUp() {
    this.jettyServerPushFunction = mock(JettyServerPushFunction.class);
    this.greetingController = new GreetingController(this.jettyServerPushFunction);
    this.request = mock(HttpServletRequest.class);
    this.pushBuilder = mock(PushBuilder.class);
    this.jettyBaseRequest = mock(Request.class);
    this.jettyPushBuilder = mock(org.eclipse.jetty.server.PushBuilder.class);

    PushBuilder pushBuilder = mock(PushBuilder.class);
    when(this.pushBuilder.path(anyString())).thenReturn(this.pushBuilder);
    when(this.jettyPushBuilder.path(anyString())).thenReturn(this.jettyPushBuilder);
    when(this.jettyBaseRequest.getPushBuilder()).thenReturn(this.jettyPushBuilder);
  }

  @Test
  public void testGreeting() {
    when(request.newPushBuilder()).thenReturn(pushBuilder);
    Greeting greeting = this.greetingController.greeting(this.request, "JavaLandTest");
    assertNotNull(greeting);
    assertEquals("Hello, JavaLandTest!", greeting.getContent());
  }

  @Test
  public void testGreetingNoPushBuilder() {
    when(request.newPushBuilder()).thenReturn(null);
    Greeting greeting = this.greetingController.greeting(this.request, "JavaLandTest");
    assertNotNull(greeting);
    assertEquals("Hello, JavaLandTest!", greeting.getContent());
  }

  @Test
  public void testPushGreeting() {
    Greeting greeting = this.greetingController.pushGreeting("JavaLandTest");
    assertNotNull(greeting);
    assertEquals("Hello, JavaLandTest!", greeting.getContent());

  }
}
