package de.holisticon.servlet4demospringbootclient.jetty;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpClientTransport;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @see https://github.com/martiner/geek-spring-jetty
 */
public class JettyClientHttpRequestFactory extends HttpClient implements ClientHttpRequestFactory, InitializingBean, DisposableBean {

  private int requestTimeout;

  public JettyClientHttpRequestFactory() {
    this(new SslContextFactory());
  }

  public JettyClientHttpRequestFactory(final SslContextFactory sslContextFactory) {
    super(sslContextFactory);
  }

  public JettyClientHttpRequestFactory(final HttpClientTransport transport, final SslContextFactory sslContextFactory) {
    super(transport, sslContextFactory);
  }

  @Override
  public ClientHttpRequest createRequest(final URI uri, final HttpMethod httpMethod) throws IOException {
    final Request request = newRequest(uri);
    request.method(httpMethod.toString());
    if (requestTimeout > 0) {
      request.timeout(requestTimeout, TimeUnit.MILLISECONDS);
    }
    postProcessHttpRequest(request);
    return new JettyClientHttpRequest(request);
  }

  protected void postProcessHttpRequest(final Request request) {
  }

  public void setRequestTimeout(final int requestTimeout) {
    this.requestTimeout = requestTimeout;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    super.start();
  }

  @Override
  public void destroy() {
    super.destroy();
  }
}