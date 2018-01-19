package de.holisticon.servlet4demo.jettyclient.jetty;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.ByteBuffer;

import org.eclipse.jetty.client.api.Response;
import org.junit.jupiter.api.Test;

public class ContentListenerTest {

  @Test
  public void testOnContent() {
    ContentListener sut = new ContentListener();

    Response response = mock(Response.class);

    sut.onContent(response, ByteBuffer.allocate(10));

    verify(response, times(1)).getHeaders();
  }
}
