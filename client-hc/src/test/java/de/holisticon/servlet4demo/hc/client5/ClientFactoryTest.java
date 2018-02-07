package de.holisticon.servlet4demo.hc.client5;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.hc.client5.http.impl.async.MinimalHttpAsyncClient;
import org.junit.jupiter.api.Test;

public class ClientFactoryTest {

  @Test
  public void testCreateClient() {
    MinimalHttpAsyncClient client = ClientFactory.createClient();

    assertTrue(client != null);
    assertTrue(client instanceof MinimalHttpAsyncClient);
  }
}
