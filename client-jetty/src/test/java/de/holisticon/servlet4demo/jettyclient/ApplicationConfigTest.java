package de.holisticon.servlet4demo.jettyclient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.jupiter.api.Test;


public class ApplicationConfigTest {

  @Test
  public void testGetHttpClient() {
    HttpClient httpClient = null;
    try {
      httpClient = ApplicationConfig.getHttpClient();
    } catch (Exception e) {
      fail("This test should not raise an exception.");
    }
    assertNotNull(httpClient);
  }

  @Test
  public void testGetHttp2Client() {
    HTTP2Client httpClient = null;
    try {
      httpClient = ApplicationConfig.getHttp2Client();
    } catch (Exception e) {
      fail("This test should not raise an exception.");
    }
    assertNotNull(httpClient);
  }

  @Test
  public void testGetSslContextFactory() {
    SslContextFactory sslContextFactory = null;
    try {
      sslContextFactory = ApplicationConfig.getSslContextFactory();
    } catch (Exception e) {
      fail("This test should not raise an exception.");
    }
    assertNotNull(sslContextFactory);
  }


}
