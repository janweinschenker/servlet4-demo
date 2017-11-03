package de.holisticon.servlet4demo.serverjetty;

import static org.eclipse.jetty.util.resource.Resource.newClassPathResource;

import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.NegotiatingServerConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = ApplicationConfig.class)
public class ApplicationConfig implements WebMvcConfigurer {

  @Value("${server.port}")
  private int httpsPort;

  @Value("${server.port.http}")
  private int httpPort;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    WebContentInterceptor interceptor = new WebContentInterceptor();
    interceptor.addCacheMapping(CacheControl.maxAge(365, TimeUnit.DAYS), "/**");
    registry.addInterceptor(interceptor);
  }

  /**
   * Create the JettyServletWebServerFactory bean.
   * @return JettyServletWebServerFactory
   */
  @Bean
  public JettyServletWebServerFactory jettyServletWebServerFactory() {
    JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
    factory.addServerCustomizers(this::configureServerForHttp2);
    return factory;
  }


  /**
   * Configure the http2 server.
   * @param server the server
   * @return the server
   */
  private Server configureServerForHttp2(Server server) {
    // HTTP Configuration
    HttpConfiguration http11Config = new HttpConfiguration();
    http11Config.setSecureScheme("https");
    http11Config.setSecurePort(httpsPort);

    // SSL Context Factory for HTTPS and HTTP/2
    SslContextFactory sslContextFactory = new SslContextFactory();
    sslContextFactory.setKeyStoreResource(newClassPathResource("sample.jks"));
    sslContextFactory.setKeyStorePassword("secret");
    sslContextFactory.setKeyManagerPassword("secret");
    sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
    sslContextFactory.setUseCipherSuitesOrder(true);

    // HTTPS Configuration
    HttpConfiguration httpsConfig = new HttpConfiguration(http11Config);
    httpsConfig.addCustomizer(new SecureRequestCustomizer());

    // HTTP/2 Connection Factory
    HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(httpsConfig);

    NegotiatingServerConnectionFactory.checkProtocolNegotiationAvailable();
    ALPNServerConnectionFactory alpnServerConnectionFactory = new ALPNServerConnectionFactory();
    alpnServerConnectionFactory.setDefaultProtocol("h2");
    alpnServerConnectionFactory.getALPNProcessor();

    // SSL Connection Factory
    SslConnectionFactory sslConnectionFactory =
        new SslConnectionFactory(sslContextFactory,
            alpnServerConnectionFactory.getProtocol());

    // HTTP/2 Connector
    ServerConnector http2Connector =
        new ServerConnector(server, sslConnectionFactory, alpnServerConnectionFactory, h2,
            new HttpConnectionFactory(httpsConfig));
    http2Connector.setPort(httpPort);
    server.addConnector(http2Connector);

    return server;
  }

}