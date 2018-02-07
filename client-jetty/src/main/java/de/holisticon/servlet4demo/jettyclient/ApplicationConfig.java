package de.holisticon.servlet4demo.jettyclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.client.http.HttpClientTransportOverHTTP2;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class ApplicationConfig {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

  @Bean
  public static HTTP2Client getHttp2Client() {
    return new HTTP2Client();
  }

  /**
   * Create a jetty http client capable to speak http/2.
   *
   * @return the client
   */
  @Bean
  public static HttpClient getHttpClient() {

    HTTP2Client http2Client = new HTTP2Client();
    HttpClientTransportOverHTTP2 transport = new HttpClientTransportOverHTTP2(
        http2Client);

    HttpClient httpClient = new HttpClient(transport, getSslContextFactory());
    httpClient.setFollowRedirects(true);
    try {
      httpClient.start();
    } catch (Exception e) {
      LOG.error("Could not start http client", e);
    }

    return httpClient;
  }

  /**
   * Create a SslContextFactory with a http2-ready cipher comparator.
   * @return an instance of a configured SslContextFactory
   */
  @Bean
  public static SslContextFactory getSslContextFactory() {

    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
    sslContextFactory.setUseCipherSuitesOrder(true);
    return sslContextFactory;
  }
}
