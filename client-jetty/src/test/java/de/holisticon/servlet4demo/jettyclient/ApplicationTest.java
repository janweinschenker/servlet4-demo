package de.holisticon.servlet4demo.jettyclient;

import de.holisticon.servlet4demo.jettyclient.jetty.JettyClientDemo;
import org.eclipse.jetty.util.FuturePromise;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit test for simple Application.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {"server.port=8443", "server.port.http=8080"})
public class ApplicationTest {

  @MockBean
  private JettyClientDemo jettyClientDemo;

  private Application application;

  @Before
  public void setUp() {
    application = new Application();
  }

  @Test
  public void testRun() {
    try {
      CommandLineRunner run = application.run();
      verify(jettyClientDemo, times(1)).performAsyncHttpRequest("localhost", 8443, "/greeting?name=JavaLand");
      verify(jettyClientDemo, times(1)).performDefaultHttpRequest("localhost", 8443, "/greeting?name=JavaLand");
    } catch (Exception e) {
      fail();
    }

  }

}
