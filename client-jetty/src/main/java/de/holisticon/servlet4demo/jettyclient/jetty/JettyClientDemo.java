package de.holisticon.servlet4demo.jettyclient.jetty;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.http2.frames.PushPromiseFrame;
import org.eclipse.jetty.util.*;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

@Component
public class JettyClientDemo {
  private static final Logger LOG = Logger.getLogger(JettyClientDemo.class);

  private HttpClient httpClient;

  private SslContextFactory sslContextFactory;

  private HTTP2Client http2Client;

  @Autowired
  public JettyClientDemo(HttpClient httpClient, HTTP2Client http2Client, SslContextFactory sslContextFactory) {
    this.httpClient = httpClient;
    this.http2Client = http2Client;
    this.sslContextFactory = sslContextFactory;
  }

  public void performAsyncHttpRequest(String host, int port, String path) {
    LOG.info("============================================= Asynchronous example ===");
    try {
      String uri = getFormatedUri(host, port, path);

      Request request =
          httpClient
              .newRequest(uri)
              .onResponseContent((response, byteBuffer) -> {
                LOG.info("content: " + BufferUtil.toString(byteBuffer));
                LOG.info("");
              });
      request.send(result -> {
        LOG.info("http version: " +
                     result
                         .getResponse()
                         .getVersion());
      });

      // watch the console log: the following message will be printed before the request has finished.
      LOG.info("request created!!!");
      Thread.sleep(5000);

    } catch (Exception e) {
      LOG.error("Exception:", e);
    }
  }


  public void performDefaultHttpRequest(String host, int port, String path) {
    LOG.info("============================================= Synchronous example ===");
    try {
      String uri = getFormatedUri(host, port, path);
      ContentResponse response = httpClient.GET(uri);

      LOG.info("http version: " + response.getVersion());
      LOG.info(response.getContentAsString());
      LOG.info("");

    } catch (Exception e) {
      LOG.error("Exception:", e);
    }
  }


  public void performHttpRequestReceivePush(String host, int port, String path, FuturePromise<Session> sessionPromise, Phaser phaser) throws Exception {

    http2Client.addBean(sslContextFactory);
    http2Client.start();

    http2Client.connect(sslContextFactory, new InetSocketAddress(host, port), new ServerSessionListener.Adapter(), sessionPromise);
    Session session = sessionPromise.get(5, TimeUnit.SECONDS);

    HttpFields requestFields = new HttpFields();
    requestFields.put("User-Agent", http2Client
        .getClass()
        .getName() + "/" + Jetty.VERSION);
    MetaData.Request metaData = new MetaData.Request("GET", new HttpURI("https://" + host + ":" + port + path), HttpVersion.HTTP_2, requestFields);
    HeadersFrame headersFrame = new HeadersFrame(metaData, null, true);
    session.newStream(headersFrame, new Promise.Adapter<>(), new Stream.Listener.Adapter() {
      @Override
      public void onHeaders(Stream stream, HeadersFrame frame) {
        LOG.info(frame);
        if (frame.isEndStream())
          phaser.arrive();
      }

      @Override
      public void onData(Stream stream, DataFrame frame, Callback callback) {
        LOG.info(frame);
        callback.succeeded();
        if (frame.isEndStream())
          phaser.arrive();
      }

      @Override
      public Stream.Listener onPush(Stream stream, PushPromiseFrame frame) {
        LOG.info(frame);
        LOG.info(frame.getMetaData());
        phaser.register();
        return this;
      }
    });

    phaser.awaitAdvanceInterruptibly(phaser.arrive(), 5, TimeUnit.SECONDS);

    http2Client.stop();
  }


  /**
   * Create uri from the three method parameters.
   *
   * @param host
   * @param port
   * @param path
   * @return
   */
  private static String getFormatedUri(String host, int port, String path) {
    return String.format("https://%s:%s%s", host, port, path);
  }

}
