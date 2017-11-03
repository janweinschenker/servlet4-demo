package de.holisticon.servlet4demo.serverjetty;

import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.holisticon.servlet4demo.Greeting;


@RestController
public class GreetingController {

  private static final Logger LOG = Logger.getLogger(GreetingController.class);

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  private JettyServerPushFunction jettyServerPushFunction;

  @Autowired
  public GreetingController(JettyServerPushFunction jettyServerPushFunction) {
    this.jettyServerPushFunction = jettyServerPushFunction;
  }

  /**
   * Method to receive a GET request for a Greeting.
   * @param request the HTTP request.
   * @param name the string containing the name
   * @return a Greeting
   */
  @RequestMapping("/greeting")
  public Greeting greeting(ServletRequest request,
      @RequestParam(value = "name", defaultValue = "World") String name) {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    if (httpServletRequest != null) {
      LOG.info("can cast to HttpServletRequest");
      if (null != httpServletRequest.newPushBuilder()) {
        PushBuilder pushBuilder = httpServletRequest.newPushBuilder();
        pushBuilder.path("/push-greeting?name=push");
        pushBuilder.push();
        LOG.info("##### has pushbuilder");
      } else {
        LOG.info("##### has no pushbuilder");
      }
    }

    jettyServerPushFunction.jettyServerPush(request);

    return new Greeting(counter.incrementAndGet(),
        String.format(template, name));
  }

  /**
   * The target endpoint of the push promise that is
   * issued in {@link #greeting(ServletRequest, String)}.
   * @param name the name
   * @return another Greeting
   */
  @RequestMapping("/push-greeting")
  public Greeting pushGreeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    return new Greeting(counter.incrementAndGet(),
        String.format(template, name));
  }

}
