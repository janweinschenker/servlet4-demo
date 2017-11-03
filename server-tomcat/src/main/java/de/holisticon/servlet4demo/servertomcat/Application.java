package de.holisticon.servlet4demo.servertomcat;

import org.apache.coyote.http2.Http2Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /**
   * Create a Tomcat customizer bean to allow Tomcat to speak http2.
   * @return the TomcatServletWebServerFactory
   */
  @Bean
  public TomcatServletWebServerFactory tomcatCustomizer() {
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
    factory.addConnectorCustomizers((connector -> {
      connector.addUpgradeProtocol(new Http2Protocol());
    }));
    return factory;
  }
}

