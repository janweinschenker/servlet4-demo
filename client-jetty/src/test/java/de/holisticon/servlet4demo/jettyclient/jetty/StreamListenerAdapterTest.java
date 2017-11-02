package de.holisticon.servlet4demo.jettyclient.jetty;

import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.util.Callback;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Phaser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StreamListenerAdapterTest {

  private StreamListener sut;

  private Phaser phaser;
  private HeadersFrame headersFrame;
  private DataFrame dataFrame;
  private Stream stream;
  private PushPromiseFrame pushPromiseFrame;
  private Callback callback;

  @Before
  public void setUp() {
    phaser = mock(Phaser.class);
    headersFrame = mock(HeadersFrame.class);
    dataFrame = mock(DataFrame.class);
    stream = mock(Stream.class);
    pushPromiseFrame = mock(PushPromiseFrame.class);
    callback = mock(Callback.class);
    sut = new StreamListener(phaser);

    when(headersFrame.isEndStream()).thenReturn(Boolean.TRUE);
    when(dataFrame.isEndStream()).thenReturn(Boolean.TRUE);
  }

  @Test
  public void testOnHeaders() {
    sut.onHeaders(stream, headersFrame);
    verify(phaser, times(1)).arrive();
  }

  @Test
  public void testOnHeadersIsNotEndStream() {
    when(headersFrame.isEndStream()).thenReturn(Boolean.FALSE);
    sut.onHeaders(stream, headersFrame);
    verify(phaser, times(0)).arrive();
  }

  @Test
  public void testOnPush() {
    Stream.Listener listener = sut.onPush(stream, pushPromiseFrame);
    assertEquals(sut, listener);
  }

  @Test
  public void testOnData() {
    sut.onData(stream, dataFrame, callback);
    verify(callback, times(1)).succeeded();
    verify(dataFrame, times(1)).isEndStream();
    verify(phaser, times(1)).arrive();
  }

  @Test
  public void testOnDataIsNotEndStream() {
    when(dataFrame.isEndStream()).thenReturn(Boolean.FALSE);
    sut.onData(stream, dataFrame, callback);
    verify(callback, times(1)).succeeded();
    verify(dataFrame, times(1)).isEndStream();
    verify(phaser, times(0)).arrive();
  }
}
