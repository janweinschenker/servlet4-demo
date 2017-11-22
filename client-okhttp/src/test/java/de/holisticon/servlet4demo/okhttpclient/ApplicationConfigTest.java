package de.holisticon.servlet4demo.okhttpclient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationConfigTest {

  private ApplicationConfig sut;

  @BeforeEach
  public void setUp() {
    sut = new ApplicationConfig();
  }

  @Test
  public void testRestTemplate() {
    RestTemplate restTemplate = sut.restTemplate();

    assertTrue(restTemplate.getRequestFactory() instanceof OkHttp3ClientHttpRequestFactory);

  }
}
