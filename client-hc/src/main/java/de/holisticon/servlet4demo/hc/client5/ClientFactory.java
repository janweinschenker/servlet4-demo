package de.holisticon.servlet4demo.hc.client5;

import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.async.MinimalHttpAsyncClient;
import org.apache.hc.core5.http2.HttpVersionPolicy;
import org.apache.hc.core5.http2.config.H2Config;
import org.apache.hc.core5.reactor.IOReactorConfig;
import org.apache.hc.core5.util.Timeout;

public class ClientFactory {

  public static MinimalHttpAsyncClient createClient() {
    final IOReactorConfig ioReactorConfig = IOReactorConfig
        .custom()
        .setSoTimeout(Timeout.ofSeconds(5))
        .build();

    H2Config.Builder builder = H2Config.copy(H2Config.DEFAULT);
    builder.setPushEnabled(true);

    final MinimalHttpAsyncClient client = HttpAsyncClients.createMinimal(
        HttpVersionPolicy.FORCE_HTTP_2, builder.build(), null, ioReactorConfig);

    client.start();
    return client;
  }
}
