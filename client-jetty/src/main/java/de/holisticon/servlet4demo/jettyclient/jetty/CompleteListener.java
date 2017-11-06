package de.holisticon.servlet4demo.jettyclient.jetty;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;

public class CompleteListener implements Response.CompleteListener {

  public static final Logger LOG = Logger.getLogger(CompleteListener.class);

  @Override
  public void onComplete(Result result) {
    LOG.info("http version: " + result
        .getResponse()
        .getVersion());
  }
}
