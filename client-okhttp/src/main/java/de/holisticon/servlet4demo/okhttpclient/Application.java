package de.holisticon.servlet4demo.okhttpclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import de.holisticon.servlet4demo.Greeting;

/**
 * Start the okHttp client demo.
 *
 * @see <a href="https://webtide.com/the-new-jetty-9-http-client/">the-new-jetty-9-http-client</a>
 * @see <a href="http://git.eclipse.org/c/jetty/org.eclipse.jetty.project.git/tree/jetty-http2/http2-client/src/test/java/org/eclipse/jetty/http2/client/Client.java">Jetty HTTP2Client Example</a>
 * @see <a href="https://blogs.oracle.com/brewing-tests/entry/http_2_with_jetty_server">http_2_with_jetty_server</a>
 */
@SpringBootApplication
public class Application {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  private RestTemplate okHttpRestTemplate;

  @Value("${server.port}")
  private int httpPort;

  @Autowired
  public Application(@Qualifier("okHttpRestTemplate") RestTemplate okHttpRestTemplate) {
    this.okHttpRestTemplate = okHttpRestTemplate;
  }

  /**
   * Well, this is a classic main method.
   *
   * @param args and these are the command line arguments.
   */
  public static void main(String[] args) {
    SpringApplication
        .run(Application.class)
        .close();
  }


  /**
   * Start the CommandLineRunner.
   *
   * @return well, a CommandLineRunner.
   */
  @Bean
  public CommandLineRunner run() {
    return args -> {
      String host = "localhost";
      String path = "/greeting?name=JavaLand";
      LOG.info("========================= restTemplate okHttp client => tomcat server ===========");
      Greeting greeting = okHttpRestTemplate.getForObject(
          "https://" + host + ":" + httpPort + path, Greeting.class);
      LOG.info(greeting.toString());
      LOG.info("=================================================================================");

    };
  }
}
