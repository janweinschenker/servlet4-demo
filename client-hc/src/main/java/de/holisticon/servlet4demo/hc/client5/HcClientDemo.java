package de.holisticon.servlet4demo.hc.client5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.impl.async.MinimalHttpAsyncClient;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.apache.hc.core5.io.ShutdownType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HcClientDemo {
  private static final Logger LOG = LoggerFactory.getLogger(HcClientDemo.class);


  public void run(String localhost, int port, String[] requestUris) throws Exception {

    final MinimalHttpAsyncClient client = ClientFactory.createClient();

    final HttpHost target = new HttpHost(localhost, port, "https");
    final Future<AsyncClientEndpoint> leaseFuture = client.lease(target, null);
    final AsyncClientEndpoint endpoint = leaseFuture.get(30, TimeUnit.SECONDS);
    try {

      final CountDownLatch latch = new CountDownLatch(requestUris.length);
      for (final String requestUri : requestUris) {
        Callback callback = new Callback(latch, requestUri);
        final SimpleHttpRequest request = SimpleHttpRequest.get(target, requestUri);
        endpoint.execute(
            SimpleRequestProducer.create(request),
            SimpleResponseConsumer.create(), callback);
      }
      latch.await();
    } finally {
      endpoint.releaseAndReuse();
    }

    LOG.debug("Shutting down");
    client.shutdown(ShutdownType.GRACEFUL);
  }
}
