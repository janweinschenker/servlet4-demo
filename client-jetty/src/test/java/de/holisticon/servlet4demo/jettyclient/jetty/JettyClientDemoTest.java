package de.holisticon.servlet4demo.jettyclient.jetty;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.util.FuturePromise;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class JettyClientDemoTest {

  private JettyClientDemo sut;

  private HttpClient httpClient;
  private HTTP2Client http2Client;
  private ContentResponse contentResponse;
  private Response response;
  private Request request;
  private SslContextFactory sslContextFactory;
  private FuturePromise<Session> sessionPromise;
  private Session session;
  private Phaser phaser;

  @Before
  public void setUp() {
    httpClient = mock(HttpClient.class);
    http2Client = mock(HTTP2Client.class);
    contentResponse = mock(ContentResponse.class);
    response = mock(Response.class);
    request = mock(Request.class);
    sslContextFactory = mock(SslContextFactory.class);
    sessionPromise = (FuturePromise<Session>) mock(FuturePromise.class);
    session = mock(Session.class);
    phaser = mock(Phaser.class);

    sut = new JettyClientDemo(httpClient, http2Client, sslContextFactory);
  }

  @Test
  public void testPerformAsyncHttpRequest() {


    when(request.onResponseContent(any(Response.ContentListener.class))).thenReturn(request);
    when(httpClient.newRequest(anyString())).thenReturn(request);

    sut.performAsyncHttpRequest("localhost", 8443, "/some/path");
    verify(httpClient, times(1)).newRequest("https://localhost:8443/some/path");
  }

  @Test
  public void testPerformDefaultHttpRequest() {
    try {
      when(httpClient.GET(anyString())).thenReturn(contentResponse);
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      fail();
    }

    sut.performDefaultHttpRequest("localhost", 8443, "/some/path");

    try {
      verify(httpClient, times(1)).GET("https://localhost:8443/some/path");
    } catch (InterruptedException | ExecutionException | TimeoutException e) {
      fail();
    }
  }

  @Test
  public void testPerformHttpRequestReceivePush() {
    try {
      when(sessionPromise.get(anyLong(), any(TimeUnit.class))).thenReturn(session);
      sut.performHttpRequestReceivePush("localhost", 8443, "/some/path", sessionPromise, phaser);
    } catch (Exception e) {
      fail();
    }
  }
}
