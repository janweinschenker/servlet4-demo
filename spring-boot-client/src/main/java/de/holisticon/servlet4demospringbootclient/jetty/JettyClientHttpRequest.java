package de.holisticon.servlet4demospringbootclient.jetty;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.BytesContentProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.AbstractClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.springframework.util.Assert.notNull;

class JettyClientHttpRequest extends AbstractClientHttpRequest {

  private final ByteArrayOutputStream bufferedOutput = new ByteArrayOutputStream();

  private final Request request;

  JettyClientHttpRequest(final Request request) {
    notNull(request, "request can't be null");
    this.request = request;
  }

  @Override
  public HttpMethod getMethod() {
    return HttpMethod.valueOf(request.getMethod());
  }

  @Override
  public String getMethodValue() {
    return request.getMethod();
  }

  @Override
  public URI getURI() {
    return request.getURI();
  }

  @Override
  protected OutputStream getBodyInternal(final HttpHeaders headers) throws IOException {
    return this.bufferedOutput;
  }

  @Override
  protected ClientHttpResponse executeInternal(final HttpHeaders headers) throws IOException {
    try {
      for (Map.Entry<String, List<String>> entry: headers.entrySet()) {
        for (String headerValue : entry.getValue()) {
          request.header(entry.getKey(), headerValue);
        }
      }
      request.content(new BytesContentProvider(bufferedOutput.toByteArray()));
      return new JettyClientHttpResponse(request.send());
    } catch (InterruptedException e) {
      throw new IOException("Unable to process request - interrupted", e);
    } catch (TimeoutException e) {
      throw new IOException("Unable to process request - timeout", e);
    } catch (ExecutionException e) {
      throw new IOException("Unable to process request - execution exception", e);
    }
  }
}