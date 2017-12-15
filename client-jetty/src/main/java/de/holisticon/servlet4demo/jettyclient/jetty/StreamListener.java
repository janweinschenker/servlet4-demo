package de.holisticon.servlet4demo.jettyclient.jetty;

import java.util.concurrent.Phaser;

import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamListener extends Stream.Listener.Adapter implements Stream.Listener {

  private static final Logger LOG = LoggerFactory.getLogger(StreamListener.class);

  private Phaser phaser;

  public StreamListener(Phaser phaser) {
    this.phaser = phaser;
  }

  @Override
  public void onHeaders(Stream stream, HeadersFrame frame) {
    LOG.info(frame.toString());
    if (frame.isEndStream()) {
      phaser.arrive();
    }
  }

  @Override
  public Stream.Listener onPush(Stream stream, PushPromiseFrame frame) {
    LOG.info(frame.toString());
    LOG.info(frame
        .getMetaData()
        .toString());
    phaser.register();
    return this;
  }

  @Override
  public void onData(Stream stream, DataFrame frame, Callback callback) {
    LOG.info(frame.toString());
    callback.succeeded();
    if (frame.isEndStream()) {
      phaser.arrive();
    }
  }

}
