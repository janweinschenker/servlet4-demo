package de.holisticon.servlet4demospringbootclient.jetty;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpField;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.AbstractClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.util.Assert.notNull;

class JettyClientHttpResponse extends AbstractClientHttpResponse {

  private final ContentResponse response;

  JettyClientHttpResponse(final ContentResponse response) {
    notNull(response, "httpMethod can't be null");
    this.response = response;
  }

  @Override
  public int getRawStatusCode() throws IOException {
    return response.getStatus();
  }

  @Override
  public String getStatusText() throws IOException {
    return response.getReason();
  }

  @Override
  public void close() {
  }

  @Override
  public InputStream getBody() throws IOException {
    return new ByteArrayInputStream(response.getContent());
  }

  @Override
  public HttpHeaders getHeaders() {
    final HttpHeaders headers = new HttpHeaders();
    for (final HttpField header: response.getHeaders()) {
      headers.add(header.getName(), header.getValue());
    }
    return headers;
  }
}