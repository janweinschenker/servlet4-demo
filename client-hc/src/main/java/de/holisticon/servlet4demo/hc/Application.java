package de.holisticon.servlet4demo.hc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.holisticon.servlet4demo.hc.client5.HcClientDemo;

@SpringBootApplication
public class Application {

  @Value("${server.port}")
  private int http2Port;

  @Autowired
  private HcClientDemo hcClientDemo;

  /**
   * This, dear checkstyle, is the main method.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    SpringApplication
        .run(Application.class)
        .close();
  }

  /**
   * Start the command line runner.
   *
   * @return a CommandLineRunner
   */
  @Bean
  public CommandLineRunner run() {

    return args -> {
      String[] requestUris = {"/greeting?name=JavaLand", "/greeting?name=OOP"};
      hcClientDemo.run(this.http2Port, requestUris);
    };
  }
}
