package de.holisticon.servlet4demo.jettyclient.jetty;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.http.HttpVersion;
import org.junit.Test;

public class CompleteListenerTest {

  @Test
  public void testOnComplete() {
    CompleteListener sut = new CompleteListener();
    Result result = mock(Result.class);
    Response response = mock(Response.class);

    when(response.getVersion()).thenReturn(HttpVersion.HTTP_2);
    when(result.getResponse()).thenReturn(response);

    sut.onComplete(result);

    verify(result, times(1)).getResponse();
    verify(response, times(1)).getVersion();
  }
}
