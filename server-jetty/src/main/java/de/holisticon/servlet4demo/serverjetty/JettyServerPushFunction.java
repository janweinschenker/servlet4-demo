package de.holisticon.servlet4demo.serverjetty;

import org.eclipse.jetty.server.Request;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;

@Component
public class JettyServerPushFunction {

  void jettyServerPush(ServletRequest request) {
    org.eclipse.jetty.server.PushBuilder jettyPushBuilder = Request
        .getBaseRequest(request)
        .getPushBuilder();
    jettyPushBuilder
        .path("/push-greeting?name=push")
        .push();
  }
}
