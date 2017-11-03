package de.holisticon.servlet4demo.serverundertow;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  /**
   * HTTP cleartext port (non https) taken from the properties.
   */
  @Value("${server.port.http}")
  public int httpPort;


  /**
   * A main method.
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public UndertowServletWebServerFactory embeddedServletContainerFactory() {
    UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
    factory.addBuilderCustomizers(this::customize);
    return factory;
  }

  void customize(Undertow.Builder builder) {
    builder
        .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
        .setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH, true)
        .addHttpListener(httpPort, "0.0.0.0");
  }
}

