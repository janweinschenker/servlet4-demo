package de.holisticon.servlet4demo.hc.client5;

import java.util.concurrent.CountDownLatch;

import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Callback implements FutureCallback<SimpleHttpResponse> {

  private static final Logger LOG = LoggerFactory.getLogger(Callback.class);

  private CountDownLatch latch;

  private String requestUri;

  public Callback(CountDownLatch latch, String requestUri) {
    this.latch = latch;
    this.requestUri = requestUri;
  }

  @Override
  public void completed(final SimpleHttpResponse response) {
    latch.countDown();
    LOG.debug(requestUri + "->" + response.getCode());
    LOG.debug(response
        .getBody()
        .getBodyText());
  }

  @Override
  public void failed(final Exception ex) {
    latch.countDown();
    LOG.debug(requestUri + "->" + ex);
  }

  @Override
  public void cancelled() {
    latch.countDown();
    LOG.debug(requestUri + " cancelled");
  }
}
