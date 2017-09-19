package de.holisticon.servlet4demospringbootclient;

import de.holisticon.servlet4demospringbootclient.dto.Greeting;
import de.holisticon.servlet4demospringbootclient.jetty.JettyClientDemo;
import de.holisticon.servlet4demospringbootclient.jetty.JettyClientHttpRequestFactory;
import okhttp3.OkHttpClient;
import org.apache.log4j.Logger;
import org.eclipse.jetty.client.HttpClientTransport;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.client.http.HttpClientTransportOverHTTP2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
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

  public static void main(String[] args) {
    SpringApplication
        .run(Application.class)
        .close();
  }

  @Autowired
  JettyClientDemo jettyClientDemo;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    try {
      HTTP2Client http2Client = new HTTP2Client();
      HttpClientTransport transport = new HttpClientTransportOverHTTP2(
          http2Client);

      JettyClientHttpRequestFactory requestFactory = new JettyClientHttpRequestFactory(transport, JettyClientDemo.getSslContextFactory());
      builder.requestFactory(requestFactory);
    } catch (Exception e) {
      LOG.error("Exception", e);
    }

    return builder.build();
  }


  @Bean
  public RestTemplate restTemplateOkHttp(RestTemplateBuilder builder) {
    try {
      OkHttpClient client = new OkHttpClient();
      OkHttp3ClientHttpRequestFactory requestFactory = new OkHttp3ClientHttpRequestFactory(client);
      builder.requestFactory(requestFactory);
    } catch (Exception e) {
      LOG.error("Exception", e);
    }

    return builder.build();
  }

  @Bean
  public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
    return args -> {
      String host = "localhost";
      int port = 8444;
      String path = "/greeting?name=JavaLand";
      jettyClientDemo.performAsyncHttpRequest(host, port, path);
      jettyClientDemo.performDefaultHttpRequest(host, port, path);
      jettyClientDemo.performHttpRequestReceivePush(host, port, path);

      Greeting greeting = restTemplate.getForObject(
          "https://" + host + ":" + port + path, Greeting.class);
      LOG.info(greeting.toString());


      Greeting greeting2 = restTemplate.getForObject(
          "https://" + host + ":" + port + "/push-greeting?name=push&name=JavaLand", Greeting.class);
      LOG.info(greeting2.toString());

      LOG.info("=========================");

      jettyClientDemo.performHttpRequestReceivePush(host, 8181, "/Servlet4Push/http2");

      System.exit(0);
    };
  }


}
