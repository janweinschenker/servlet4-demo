package de.holisticon.servlet4demo.okhttpclient;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import de.holisticon.servlet4demo.Greeting;

/**
 * Unit test for simple Application.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)

public class ApplicationTest {

  private Application sut;

  @Autowired
  @Qualifier("okHttpRestTemplate")
  private RestTemplate okHttpRestTemplate;

  @TestConfiguration
  static class TestContextConfiguration {
    private RestTemplate mockRestTemplate = mock(RestTemplate.class);

    @Bean(name = "okHttpRestTemplate")
    public RestTemplate restTemplate() {
      Greeting greeting = new Greeting();
      greeting.setContent("Hello, Test! ");
      greeting.setId(1);

      String string = "Teststring... Teststring... Teststring... Teststring... Teststring... ";
      when(mockRestTemplate.getForObject(anyString(), same(Greeting.class))).thenReturn(greeting);
      when(mockRestTemplate.getForObject(anyString(), same(String.class))).thenReturn(string);

      return mockRestTemplate;
    }
  }


  @BeforeEach
  public void setUp() {
    sut = new Application(okHttpRestTemplate);
  }

  @Test
  public void testRun() {
    try {
      sut.run();
      verify(okHttpRestTemplate, times(1))
          .getForObject(anyString(), same(Greeting.class));
    } catch (Exception e) {
      fail("This test should not raise an exception.");
    }
  }

  @Test
  public void contextLoads() {
    assertNotNull(okHttpRestTemplate);
  }

}
