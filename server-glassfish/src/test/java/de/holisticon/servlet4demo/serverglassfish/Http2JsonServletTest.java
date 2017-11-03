package de.holisticon.servlet4demo.serverglassfish;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class Http2JsonServletTest {

  private Http2JsonServlet sut;

  private HttpServletRequest request;
  private HttpServletResponse response;

  private StringWriter stringWriter = new StringWriter();

  @Before
  public void setUp() {
    sut = new Http2JsonServlet();

    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    PrintWriter printWriter = new PrintWriter(stringWriter);
    try {
      when(response.getWriter()).thenReturn(printWriter);
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testDoGet() {
    try {
      sut.doGet(request, response);
      verify(response, times(1)).setContentType("application/json");
      verify(response, times(1)).getWriter();
      assertEquals("{\"id\":39,\"content\":\"Hello, push!\"}", stringWriter.toString());

    } catch (ServletException e) {
      fail();
    } catch (IOException e) {
      fail();
    }
  }
}
