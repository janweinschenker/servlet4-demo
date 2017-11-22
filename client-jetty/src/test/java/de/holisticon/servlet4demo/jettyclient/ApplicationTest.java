package de.holisticon.servlet4demo.jettyclient;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.holisticon.servlet4demo.jettyclient.jetty.JettyClientDemo;

/**
 * Unit test for simple Application.
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = {"server.port=8443", "server.port.http=8080"})
public class ApplicationTest {

  @MockBean
  private JettyClientDemo jettyClientDemo;

  private Application application;

  @BeforeEach
  public void setUp() {
    application = new Application();
  }

  @Test
  public void testRun() {
    try {
      application.run();
      verify(jettyClientDemo, times(1)).performAsyncHttpRequest("localhost", 8443, "/greeting?name=JavaLand");
      verify(jettyClientDemo, times(1)).performDefaultHttpRequest("localhost", 8443, "/greeting?name=JavaLand");
    } catch (Exception e) {
      fail("This test should not raise an exception.");
    }

  }

}
