package de.holisticon.servlet4demo.hc.client5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.async.methods.SimpleHttpRequest;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.apache.hc.client5.http.async.methods.SimpleRequestProducer;
import org.apache.hc.client5.http.async.methods.SimpleResponseConsumer;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.async.MinimalHttpAsyncClient;
import org.apache.hc.core5.concurrent.FutureCallback;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.http2.config.H2Config;
import org.apache.hc.core5.io.ShutdownType;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HcClientDemo {
  private static final Logger LOG = LoggerFactory.getLogger(HcClientDemo.class);

  public void run(int port, String[] requestUris) throws Exception {

    final IOReactorConfig ioReactorConfig = IOReactorConfig
        .custom()
        .setSoTimeout(Timeout.ofSeconds(5))
        .build();

    H2Config.Builder builder = H2Config.copy(H2Config.DEFAULT);
    builder.setPushEnabled(true);

    final MinimalHttpAsyncClient client = HttpAsyncClients.createMinimal(
        HttpVersionPolicy.FORCE_HTTP_2, builder.build(), null, ioReactorConfig);

    client.start();

    final HttpHost target = new HttpHost("localhost", port, "https");
    final Future<AsyncClientEndpoint> leaseFuture = client.lease(target, null);
    final AsyncClientEndpoint endpoint = leaseFuture.get(30, TimeUnit.SECONDS);
    try {

      final CountDownLatch latch = new CountDownLatch(requestUris.length);
      for (final String requestUri : requestUris) {
        final SimpleHttpRequest request = SimpleHttpRequest.get(target, requestUri);
        endpoint.execute(
            SimpleRequestProducer.create(request),
            SimpleResponseConsumer.create(),
            new FutureCallback<>() {

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

            });
      }
      latch.await();
    } finally {
      endpoint.releaseAndReuse();
    }

    LOG.debug("Shutting down");
    client.shutdown(ShutdownType.GRACEFUL);
  }
}
