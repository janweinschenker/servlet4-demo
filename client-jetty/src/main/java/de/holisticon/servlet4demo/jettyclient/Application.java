package de.holisticon.servlet4demo.jettyclient;

import de.holisticon.servlet4demo.jettyclient.jetty.JettyClientDemo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Hello world!
 *
 * @see <a href="https://webtide.com/the-new-jetty-9-http-client/">the-new-jetty-9-http-client</a>
 * @see <a href="http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tree/jetty-http2/http2-client/src/test/java/org/eclipse/jetty/http2/client/Client.java">Jetty HTTP2Client Example</a>
 * @see <a href="https://blogs.oracle.com/brewing-tests/entry/http_2_with_jetty_server">http_2_with_jetty_server</a>
 */
@SpringBootApplication
public class Application {

  private static final Logger LOG = Logger.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication
        .run(Application.class)
        .close();
  }

  @Autowired
  JettyClientDemo jettyClientDemo;

  @Bean
  public CommandLineRunner run() throws Exception {
    return args -> {
      String host = "localhost";
      int port = 8444;
      String path = "/greeting?name=JavaLand";
      jettyClientDemo.performAsyncHttpRequest(host, port, path);
      jettyClientDemo.performDefaultHttpRequest(host, port, path);
      jettyClientDemo.performHttpRequestReceivePush(host, port, path);

      LOG.info("========================= jetty - receive server push ");
      jettyClientDemo.performHttpRequestReceivePush(host, 8181, "/Servlet4Push/http2");
      System.exit(0);
    };
  }


}
