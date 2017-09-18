package de.holisticon.servlet4demospringbootclient.util;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.api.Stream.Listener.Adapter;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.util.Callback;

import java.nio.charset.Charset;
import java.util.concurrent.Phaser;

/**
 * Created by janweinschenker on 14.12.16.
 */
public class StreamListener extends Adapter {

  private static final Logger LOG = Logger.getLogger(StreamListener.class);
  private Phaser phaser;

  public StreamListener(Phaser phaser) {
    this.phaser = phaser;
  }

  @Override
  public Stream.Listener onPush(Stream stream, PushPromiseFrame frame) {
    LOG.debug("onPush: " + frame);
    phaser.register();
    return this;
  }

  @Override
  public void onHeaders(Stream stream, HeadersFrame frame) {
    LOG.debug("onHeaders: " + frame);
    LOG.debug("http version: " + frame
        .getMetaData()
        .getHttpVersion());

    if (frame.isEndStream()) {
      phaser.arrive();
    }
  }

  @Override
  public void onData(Stream stream, DataFrame frame, Callback callback) {
    LOG.debug("onData: " + new String(frame
                                          .toString()
                                          .getBytes(), Charset.forName("UTF8")));
    callback.succeeded();
    if (frame.isEndStream()) {
      phaser.arrive();
    }
  }
}
