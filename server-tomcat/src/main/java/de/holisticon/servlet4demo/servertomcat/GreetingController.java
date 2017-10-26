package de.holisticon.servlet4demo.servertomcat;

import de.holisticon.servlet4demo.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

  private static final String TEMPLATE = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  @RequestMapping("/greeting")
  public Greeting greeting(
      HttpServletRequest request,
      @RequestParam(value = "name", defaultValue = "World") String name) {

    PushBuilder pushBuilder = request.newPushBuilder();
    pushBuilder.path("/push-greeting?name=push");
    pushBuilder.push();

    return new Greeting(counter.incrementAndGet(),
                        String.format(TEMPLATE, name));
  }

  @RequestMapping("/push-greeting")
  public Greeting pushGreeting(
      @RequestParam(value = "name",
          defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(),
                        String.format(TEMPLATE, name));
  }
}
