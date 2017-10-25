package de.holisticon.servlet4demo.serverundertow;

import io.undertow.UndertowOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

@SpringBootApplication
public class Application {

  @Value("${server.port.http}")
  public int httpPort;


  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  UndertowServletWebServerFactory embeddedServletContainerFactory() {
    UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
    factory.addBuilderCustomizers(
        builder -> builder
            .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
            .addHttpListener(httpPort, "0.0.0.0"));
    return factory;
  }
}

