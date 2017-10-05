package de.holisticon.servlet4demojettyclient;

import de.holisticon.servlet4demojettyclient.dto.Greeting;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

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

  private RestTemplate okHttpRestTemplate;

  @Autowired
  public Application(@Qualifier("okHttpRestTemplate") RestTemplate okHttpRestTemplate) {
    this.okHttpRestTemplate = okHttpRestTemplate;
  }

  public static void main(String[] args) {
    SpringApplication
        .run(Application.class)
        .close();
  }


  @Bean
  public CommandLineRunner run() throws Exception {
    return args -> {
      String host = "localhost";
      int port = 8444;
      String path = "/greeting?name=JavaLand";
      LOG.info("========================= restTemplate jetty -> jetty server");
      Greeting greeting = okHttpRestTemplate.getForObject(
          "https://" + host + ":" + port + path, Greeting.class);
      LOG.info(greeting.toString());

      LOG.info("========================= okHttpTemplate -> jetty server");
      Greeting greeting3 = okHttpRestTemplate.getForObject(
          "https://" + host + ":" + port + path, Greeting.class);
      LOG.info(greeting3.toString());

      LOG.info("========================= okHttpTemplate -> glassfish");
      String response = okHttpRestTemplate.getForObject(
          "https://" + host + ":" + 8181 + "/Servlet4Push/http2", String.class);
      LOG.info(response.substring(0, 20));
    };
  }


}
