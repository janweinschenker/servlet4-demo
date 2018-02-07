package de.holisticon.servlet4demo.hc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;

import de.holisticon.servlet4demo.hc.client5.HcClientDemo;

public class ApplicationTest {

  private Application sut;

  @BeforeEach
  public void setUp() {
    sut = new Application();
  }

  @Test
  public void testRun() {
    HcClientDemo demo = mock(HcClientDemo.class);
    sut.setHcClientDemo(demo);

    CommandLineRunner commandLineRunner = sut.run();

    try {
      commandLineRunner.run("test");
    } catch (Exception e) {
      fail("This test should not fail.");
    }
    assertNotNull(commandLineRunner);

  }
}
