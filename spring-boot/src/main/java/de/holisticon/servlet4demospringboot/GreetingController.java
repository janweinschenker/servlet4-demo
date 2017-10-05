package de.holisticon.servlet4demospringboot;

import de.holisticon.servlet4demospringboot.dto.Greeting;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Request;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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


    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    if (httpServletRequest != null) {
      LOG.info("can cast to HttpServletRequest");
      if (request != null && httpServletRequest.newPushBuilder() != null) {
        PushBuilder pushBuilder = httpServletRequest.newPushBuilder();
        pushBuilder.path("/push-greeting?name=push");
        pushBuilder.push();
        LOG.info("##### has pushbuilder");
      } else {
        LOG.info("##### has no pushbuilder");
      }
    }


    org.eclipse.jetty.server.PushBuilder jettyPushBuilder = Request
        .getBaseRequest(request)
        .getPushBuilder();
    jettyPushBuilder
        .path("/push-greeting?name=push")
        .push();
    return new Greeting(counter.incrementAndGet(),
                        String.format(template, name));
  }

  @RequestMapping("/push-greeting")
  public Greeting pushGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(),
                        String.format(template, name));
  }

}
