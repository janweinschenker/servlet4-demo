package de.holisticon.servlet4demo.serverjetty;

import javax.servlet.ServletRequest;

import org.assertj.core.util.VisibleForTesting;
import org.eclipse.jetty.server.Request;
import org.springframework.stereotype.Component;

@Component
public class JettyServerPushFunction {


  /**
   * Create a PushBuilder for the given request an perform a push.
   *
   * @param request the request from which to get the PushBuilder
   */
  @VisibleForTesting
  void jettyServerPush(ServletRequest request) {
    org.eclipse.jetty.server.PushBuilder jettyPushBuilder = Request
        .getBaseRequest(request)
        .getPushBuilder();
    jettyPushBuilder
        .path("/push-greeting?name=push")
        .push();
  }


}
