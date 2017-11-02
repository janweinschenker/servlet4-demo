package de.holisticon.servlet4demo.serverundertow;

import de.holisticon.servlet4demo.Greeting;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.ServerConnection;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.PushBuilder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GreetingControllerTest {

  private GreetingController sut;
  private HttpServletRequestImpl request;
  private HttpServerExchange exchange;
  private ServerConnection connection;
  private PushBuilder pushBuilder;

  @Before
  public void setUp() {
    sut = new GreetingController();
    connection = mock(ServerConnection.class);

    exchange = mock(HttpServerExchange.class);
    request = mock(HttpServletRequestImpl.class);
    pushBuilder = mock(PushBuilder.class);

    when(exchange.getConnection()).thenReturn(connection);
    when(exchange.getRequestHeaders()).thenReturn(new HeaderMap());
    when(request.getExchange()).thenReturn(exchange);

  }

  @Test
  public void testGreeting() {
    Greeting test = sut.greeting(request, "test");
    assertEquals("Hello, test!", test.getContent());
    verify(request, times(1)).getExchange();
    verify(exchange, times(1)).getConnection();
    verify(connection, times(1)).pushResource(anyString(), any(HttpString.class), any(HeaderMap.class));
  }

    @Test
    public void testGreetingPushBuilder() {
      when(request.newPushBuilder()).thenReturn(pushBuilder);
      when(pushBuilder.path(anyString())).thenReturn(pushBuilder);

      Greeting test = sut.greeting(request, "test");
      assertEquals("Hello, test!", test.getContent());
      verify(request, times(1)).getExchange();
      verify(exchange, times(1)).getConnection();
      verify(connection, times(1)).pushResource(anyString(), any(HttpString.class), any(HeaderMap.class));
    }

  @Test
  public void testPushGreeting() {
    Greeting test = sut.pushGreeting("push");
    assertEquals("Hello, push!", test.getContent());
  }
}
