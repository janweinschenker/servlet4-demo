package de.holisticon.servlet4demo.jettyclient;

import java.util.concurrent.Phaser;

import org.eclipse.jetty.util.FuturePromise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import de.holisticon.servlet4demo.jettyclient.jetty.JettyClientDemo;

/**
 * Start the Jetty client demo application.
 *
 * @see <a href="https://webtide.com/the-new-jetty-9-http-client/">the-new-jetty-9-http-client</a>
 * @see <a href="http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tree/jetty-http2/http2-client/src/test/java/org/eclipse/jetty/http2/client/Client.java">Jetty HTTP2Client Example</a>
 * @see <a href="https://blogs.oracle.com/brewing-tests/entry/http_2_with_jetty_server">http_2_with_jetty_server</a>
 */
@SpringBootApplication
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public class Application {

  @Value("${server.port}")
  private int http2Port;

  @Autowired
  private JettyClientDemo jettyClientDemo;

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
      String host = "localhost";
      String path = "/greeting?name=JavaLand";
      jettyClientDemo.performAsyncHttpRequest(host, http2Port, path);
      jettyClientDemo.performDefaultHttpRequest(host, http2Port, path);
      jettyClientDemo.performHttpRequestReceivePush(host, http2Port, path,
          new FuturePromise<>(), new Phaser(2), () -> jettyClientDemo.initHttp2Client());
    };
  }


}
