package de.holisticon.servlet4demo.serverjetty;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

  @Autowired
  private GreetingController greetingController;

  @Test
  public void contextLoads() {
    assertNotNull(greetingController);
  }

}
