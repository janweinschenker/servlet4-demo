package de.holisticon.servlet4demo.jettyclient;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.client.http.HttpClientTransportOverHTTP2;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Autowired
  private SslContextFactory sslContextFactory;


  @Bean
  public HTTP2Client getHttp2Client(){
    return new HTTP2Client();
  }

  /**
   * Create a jetty http client capable to speak http/2.
   *
   * @return
   * @throws Exception
   */
  @Bean
  public HttpClient getHttpClient() throws Exception {
    HTTP2Client http2Client = new HTTP2Client();
    HttpClientTransportOverHTTP2 transport = new HttpClientTransportOverHTTP2(
        http2Client);

    HttpClient httpClient = new HttpClient(transport, sslContextFactory);
    httpClient.setFollowRedirects(true);
    httpClient.start();

    return httpClient;
  }

  @Bean
  public SslContextFactory getSslContextFactory() {

    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
    sslContextFactory.setUseCipherSuitesOrder(true);
    return sslContextFactory;
  }
}
