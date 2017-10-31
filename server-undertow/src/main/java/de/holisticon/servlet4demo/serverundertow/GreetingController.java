package de.holisticon.servlet4demo.serverundertow;

import de.holisticon.servlet4demo.Greeting;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import io.undertow.util.Methods;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.PushBuilder;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class GreetingController {

  private static final Logger LOG = Logger.getLogger(GreetingController.class);

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  /**
   * @param request
   * @param name
   * @return
   * @see org.springframework.web.servlet.mvc.method.annotation.ServletRequestMethodArgumentResolver
   */
  @RequestMapping("/greeting")
  public Greeting greeting(ServletRequest request, @RequestParam(value = "name", defaultValue = "World") String name) {

    HttpServletRequestImpl httpServletRequest = (HttpServletRequestImpl) request;
    HttpServerExchange exchange = httpServletRequest.getExchange();
    exchange
        .getConnection()
        .pushResource("/push-greeting?name=push", Methods.GET, exchange.getRequestHeaders());
    LOG.info("undertow pushBuilder has pushed resource.");

    PushBuilder pushBuilder = httpServletRequest.newPushBuilder();
    if (pushBuilder != null) {
      pushBuilder.path("/push-greeting?name=push");
      pushBuilder.push();
    } else {
      LOG.info("No servlet4 pushBuilder!");
    }

    return new Greeting(counter.incrementAndGet(),
                        String.format(template, name));
  }

  @RequestMapping("/push-greeting")
  public Greeting pushGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(),
                        String.format(template, name));
  }

}
