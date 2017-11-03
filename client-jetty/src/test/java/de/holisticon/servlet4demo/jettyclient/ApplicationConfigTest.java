package de.holisticon.servlet4demo.jettyclient;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class ApplicationConfigTest {

  @Test
  public void testGetHttpClient() {
    HttpClient httpClient = null;
    try {
      httpClient = ApplicationConfig.getHttpClient();
    } catch (Exception e) {
      fail();
    }
    assertNotNull(httpClient);
  }

  @Test
  public void testGetHttp2Client() {
    HTTP2Client httpClient = null;
    try {
      httpClient = ApplicationConfig.getHttp2Client();
    } catch (Exception e) {
      fail();
    }
    assertNotNull(httpClient);
  }

  @Test
  public void testGetSslContextFactory() {
    SslContextFactory sslContextFactory = null;
    try {
      sslContextFactory = ApplicationConfig.getSslContextFactory();
    } catch (Exception e) {
      fail();
    }
    assertNotNull(sslContextFactory);
  }


}
