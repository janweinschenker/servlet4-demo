package de.holisticon.servlet4demo.serverjetty;


import org.eclipse.jetty.server.HttpChannel;
import org.eclipse.jetty.server.PushBuilder;
import org.eclipse.jetty.server.Request;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;

import static org.mockito.Mockito.*;

public class JettyServerPushFunctionTest {

  private JettyServerPushFunction sut;

  private Request request;

  @Before
  public void setUp() {
    sut = new JettyServerPushFunction();
    request = mock(Request.class);
    ServletContext ctx = mock(ServletContext.class);
    PushBuilder pushBuilder = mock(PushBuilder.class);
    when(ctx.getRealPath(anyString())).thenReturn("/path/index.html");
    when(request.getPushBuilder()).thenReturn(pushBuilder);
    when(pushBuilder.path(anyString())).thenReturn(pushBuilder);
  }

  @Test
  public void testJettyServerPush() {
    sut.jettyServerPush(request);

    verify(request, times(1)).getPushBuilder();


  }
}
