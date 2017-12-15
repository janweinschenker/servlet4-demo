package de.holisticon.servlet4demo.jettyclient.jetty;

import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompleteListener implements Response.CompleteListener {

  public static final Logger LOG = LoggerFactory.getLogger(CompleteListener.class);

  @Override
  public void onComplete(Result result) {
    LOG.info("http version: " + result
        .getResponse()
        .getVersion());
  }
}
