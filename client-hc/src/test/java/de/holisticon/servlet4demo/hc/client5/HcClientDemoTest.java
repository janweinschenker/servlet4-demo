package de.holisticon.servlet4demo.hc.client5;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.hc.client5.http.impl.async.MinimalHttpAsyncClient;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.nio.AsyncClientEndpoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HcClientDemoTest {

  private HcClientDemo sut;

  @BeforeEach
  public void setUp() {
    sut = new HcClientDemo();
  }

  @Test
  public void testRun() {
    String[] requestUris = {"/greeting?name=JavaLand", "/greeting?name=OOP"};
    MinimalHttpAsyncClient client = mock(MinimalHttpAsyncClient.class);
    Future<AsyncClientEndpoint> future = mock(Future.class);
    AsyncClientEndpoint endpoint = mock(AsyncClientEndpoint.class);
    try {
      when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(endpoint);
    } catch (InterruptedException e) {
      fail("This test is not expected to fail.");
    } catch (ExecutionException e) {
      fail("This test is not expected to fail.");
    } catch (TimeoutException e) {
      fail("This test is not expected to fail.");
    }
    when(client.lease(any(HttpHost.class), any())).thenReturn(future);
    try {
      sut.setClient(client);
      sut.run("localhost", 443, requestUris);
    } catch (Exception e) {
      fail("This test is not expected to fail.");
    }
  }

  @Test
  public void testRunTimeout() {
    String[] requestUris = {"/greeting?name=JavaLand", "/greeting?name=OOP"};
    MinimalHttpAsyncClient client = mock(MinimalHttpAsyncClient.class);
    Future<AsyncClientEndpoint> future = mock(Future.class);
    try {
      when(future.get(anyLong(), any(TimeUnit.class))).thenThrow(InterruptedException.class);
    } catch (InterruptedException e) {
      fail("This test is not expected to fail.");
    } catch (ExecutionException e) {
      fail("This test is not expected to fail.");
    } catch (TimeoutException e) {
      fail("This test is not expected to fail.");
    }
    when(client.lease(any(HttpHost.class), any())).thenReturn(future);
    try {
      sut.setClient(client);
      sut.run("localhost", 443, requestUris);
    } catch (Exception e) {
      fail("This test is not expected to fail.");
    }
  }

  @Test
  public void testInit() {
    MinimalHttpAsyncClient client = sut.getClient();
    assertNotNull(client);
    assertTrue(client instanceof MinimalHttpAsyncClient);

  }
}
