package de.holisticon.servlet4demospringboot;

import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import static org.eclipse.jetty.util.resource.Resource.newClassPathResource;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public JettyServletWebServerFactory jettyServletWebServerFactory() {
    JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
    factory.addServerCustomizers(this::configureServerForHttp2);
    return factory;
  }

  private Server configureServerForHttp2(Server server) {
    // HTTP Configuration
    HttpConfiguration http11Config = new HttpConfiguration();
    http11Config.setSecureScheme("https");
    http11Config.setSecurePort(8443);

    // SSL Context Factory for HTTPS and HTTP/2
    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setKeyStoreResource(newClassPathResource("sample.jks"));
    sslContextFactory.setKeyStorePassword("secret");
    sslContextFactory.setKeyManagerPassword("secret");
    sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);

    // HTTPS Configuration
    HttpConfiguration httpsConfig = new HttpConfiguration(http11Config);
    httpsConfig.addCustomizer(new SecureRequestCustomizer());

    // HTTP/2 Connection Factory
    HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(httpsConfig);

    NegotiatingServerConnectionFactory.checkProtocolNegotiationAvailable();
    ALPNServerConnectionFactory alpnServerConnectionFactory = new ALPNServerConnectionFactory();
    alpnServerConnectionFactory.setDefaultProtocol("h2");

    // SSL Connection Factory
    SslConnectionFactory sslConnectionFactory = new SslConnectionFactory(sslContextFactory, alpnServerConnectionFactory.getProtocol());

    // HTTP/2 Connector
    ServerConnector http2Connector =
        new ServerConnector(server, sslConnectionFactory, alpnServerConnectionFactory, h2, new HttpConnectionFactory(httpsConfig));
    http2Connector.setPort(8444);
    server.addConnector(http2Connector);

    return server;
  }


}
