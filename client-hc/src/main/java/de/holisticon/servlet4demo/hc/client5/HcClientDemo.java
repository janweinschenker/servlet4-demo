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
import org.assertj.core.util.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HcClientDemo {
  private static Logger LOG = LoggerFactory.getLogger(HcClientDemo.class);

  private MinimalHttpAsyncClient client;


  public HcClientDemo() {
    this.client = client = ClientFactory.createClient();
  }

  public void run(String localhost, int port, String[] requestUris) throws Exception {

    HttpHost target = new HttpHost(localhost, port, "https");
    Future<AsyncClientEndpoint> leaseFuture = client.lease(target, null);
    AsyncClientEndpoint endpoint = null;
    try {
      endpoint = leaseFuture.get(3, TimeUnit.SECONDS);
      CountDownLatch latch = new CountDownLatch(requestUris.length);
      for (String requestUri : requestUris) {
        Callback callback = new Callback(latch, requestUri);
        SimpleHttpRequest request = SimpleHttpRequest.get(target, requestUri);
        endpoint.execute(
            SimpleRequestProducer.create(request),
            SimpleResponseConsumer.create(), callback);
      }
      latch.await(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      LOG.error("TimeOut");
    } finally {
      if (endpoint != null) {
        endpoint.releaseAndReuse();
      }
    }
    LOG.debug("Shutting down");
    client.shutdown(ShutdownType.GRACEFUL);
  }


  @VisibleForTesting
  MinimalHttpAsyncClient getClient() {
    return client;
  }

  @VisibleForTesting
  void setClient(MinimalHttpAsyncClient client) {
    this.client = client;
  }

}
