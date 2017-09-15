package de.holisticon.servlet4demospringboot;

import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import static org.eclipse.jetty.util.resource.Resource.newClassPathResource;

@SpringBootApplication
public class Servlet4DemoSpringBootApplication {

  public static void main(String[] args) {
    SpringApplication.run(Servlet4DemoSpringBootApplication.class, args);
  }

  @Value("${http.version:'HTTP2'}")
  private String httpVersion;


  @Bean
  public JettyServletWebServerFactory jettyServletWebServerFactory() {
    JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
//    factory.addServerCustomizers(this::configure);
    factory.addServerCustomizers(server -> {
      // HTTP Configuration
      HttpConfiguration http_config = new HttpConfiguration();
      http_config.setSecureScheme("https");
      http_config.setSecurePort(8443);

      // SSL Context Factory for HTTPS and HTTP/2
      SslContextFactory sslContextFactory = new SslContextFactory();
      sslContextFactory.setKeyStoreResource(newClassPathResource("sample.jks"));
      sslContextFactory.setKeyStorePassword("secret");
      sslContextFactory.setKeyManagerPassword("secret");
      sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);

      // HTTPS Configuration
      HttpConfiguration https_config = new HttpConfiguration(http_config);
      https_config.addCustomizer(new SecureRequestCustomizer());

      // HTTP/2 Connection Factory
      HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(https_config);

      NegotiatingServerConnectionFactory.checkProtocolNegotiationAvailable();
      ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
      alpn.setDefaultProtocol("h2");

      // SSL Connection Factory
      SslConnectionFactory ssl = new SslConnectionFactory(sslContextFactory, alpn.getProtocol());

      // HTTP/2 Connector
      ServerConnector http2Connector =
          new ServerConnector(server, ssl, alpn, h2, new HttpConnectionFactory(https_config));
      http2Connector.setPort(8444);
      server.addConnector(http2Connector);
    });
    return factory;
  }


}
