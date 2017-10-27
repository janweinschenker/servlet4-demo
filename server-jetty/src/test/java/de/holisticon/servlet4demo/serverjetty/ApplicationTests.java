package de.holisticon.servlet4demo.serverjetty;

import de.holisticon.servlet4demo.serverjetty.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

  @Test
  public void contextLoads() {
    Application.main(new String[]{
    });
  }

}
