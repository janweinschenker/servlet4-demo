package de.holisticon.servlet4demo.okhttpclient;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;

public class ApplicationConfigTest {

  private ApplicationConfig sut;

  @Before
  public void setUp() {
    sut = new ApplicationConfig();
  }

  @Test
  public void testRestTemplate() {
    RestTemplate restTemplate = sut.restTemplate();

    assertTrue(restTemplate.getRequestFactory() instanceof OkHttp3ClientHttpRequestFactory);

  }
}
