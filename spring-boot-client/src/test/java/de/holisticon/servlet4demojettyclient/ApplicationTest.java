package de.holisticon.servlet4demojettyclient;

import de.holisticon.servlet4demojettyclient.dto.Greeting;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

/**
 * Unit test for simple Application.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
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


  @Before
  public void setUp() {
    sut = new Application(okHttpRestTemplate);
  }

  @Test
  public void testRun() {
    try {
      sut.run();
      verify(okHttpRestTemplate, Mockito.times(2))
          .getForObject(anyString(), same(Greeting.class));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void contextLoads() {
  }

}
