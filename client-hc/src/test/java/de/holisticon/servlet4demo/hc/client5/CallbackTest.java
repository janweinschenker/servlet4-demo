package de.holisticon.servlet4demo.hc.client5;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CountDownLatch;

import org.apache.hc.client5.http.async.methods.SimpleBody;
import org.apache.hc.client5.http.async.methods.SimpleHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CallbackTest {

  private Callback sut;

  private CountDownLatch latch;

  @BeforeEach
  public void setUp() {
    latch = mock(CountDownLatch.class);
    sut = new Callback(latch, "test");
  }

  @Test
  public void testCompleted() {
    SimpleHttpResponse response = mock(SimpleHttpResponse.class);
    SimpleBody body = mock(SimpleBody.class);
    when(response.getBody()).thenReturn(body);
    sut.completed(response);
    verify(latch, times(1)).countDown();
  }

  @Test
  public void testFailed() {
    Exception ex = mock(Exception.class);
    sut.failed(ex);
    verify(latch, times(1)).countDown();
  }

  @Test
  public void testCancelled() {
    sut.cancelled();
    verify(latch, times(1)).countDown();
  }
}
