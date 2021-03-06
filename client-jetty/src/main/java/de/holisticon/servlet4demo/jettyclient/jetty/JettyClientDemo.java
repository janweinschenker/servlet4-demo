package de.holisticon.servlet4demo.jettyclient.jetty;

import java.net.InetSocketAddress;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.util.FuturePromise;
import org.eclipse.jetty.util.Jetty;
import org.eclipse.jetty.util.Promise;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JettyClientDemo {
  private static final Logger LOG = LoggerFactory.getLogger(JettyClientDemo.class);

  private HttpClient httpClient;

  private SslContextFactory sslContextFactory;

  private HTTP2Client http2Client;

  /**
   * Construct the {@link JettyClientDemo}.
   *
   * @param httpClient        the http2 client
   * @param http2Client       the low-level-api http2 client
   * @param sslContextFactory the ssl context factory
   */
  @Autowired
  public JettyClientDemo(
      HttpClient httpClient, HTTP2Client http2Client,
      SslContextFactory sslContextFactory) {
    this.httpClient = httpClient;
    this.http2Client = http2Client;
    this.sslContextFactory = sslContextFactory;
  }

  /**
   * Perform an asnychronous http request against a server.
   *
   * @param host the hostname
   * @param port the port
   * @param path the request path
   */
  public void performAsyncHttpRequest(String host, int port, String path) {
    LOG.info("============================================= Asynchronous example ===");
    try {
      httpClient
          .newRequest(getFormatedUri(host, port, path))
          .onResponseContent(new ContentListener())
          .send(new CompleteListener());
      LOG.info("request created!!!");
      Thread.sleep(5000);

    } catch (Exception e) {
      LOG.error("Exception:", e);
    }
  }


  /**
   * Perform a synchronous http request.
   *
   * @param host the hostname
   * @param port the port
   * @param path the request path
   */
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


  /**
   * Perform an http request and wait for a possibly incoming push promise.
   *
   * @param host           the hostname
   * @param port           the port
   * @param path           the request path
   * @param sessionPromise the session promise object
   * @param phaser         the phaser
   * @throws Exception may occur when client is started or stopped
   */
  public void performHttpRequestReceivePush(
      String host, int port, String path,
      FuturePromise<Session> sessionPromise, Phaser phaser, ClientInitializer initializer)
      throws Exception {

    initializer.init();
    performHttpRequestReceivePush(host, port, path, sessionPromise, phaser);
    http2Client.stop();
  }

  /**
   * Perform an http request and wait for a possibly incoming push promise.
   *
   * @param host           the hostname
   * @param port           the port
   * @param path           the request path
   * @param sessionPromise the session promise object
   * @param phaser         the phaser
   * @throws Exception may occur when client is started or stopped
   */
  public void performHttpRequestReceivePush(
      String host, int port, String path,
      FuturePromise<Session> sessionPromise, Phaser phaser)
      throws Exception {
    http2Client.connect(sslContextFactory, new InetSocketAddress(host, port),
        new ServerSessionListener.Adapter(), sessionPromise);
    Session session = sessionPromise.get(5, TimeUnit.SECONDS);

    HttpFields requestFields = new HttpFields();
    requestFields.put("User-Agent", http2Client
        .getClass()
        .getName() + "/" + Jetty.VERSION);
    MetaData.Request metaData =
        new MetaData.Request("GET", new HttpURI("https://" + host + ":" + port + path),
            HttpVersion.HTTP_2, requestFields);
    HeadersFrame headersFrame = new HeadersFrame(metaData, null, true);
    session.newStream(headersFrame, new Promise.Adapter<>(), new StreamListener(phaser));

    phaser.awaitAdvanceInterruptibly(phaser.arrive(), 5, TimeUnit.SECONDS);
  }

  public void initHttp2Client() {
    http2Client.addBean(sslContextFactory);
    try {
      http2Client.start();
    } catch (Exception e) {
      LOG.error("Exception while initializing http2 client.", e);
    }

  }


  /**
   * Create uri from the three method parameters.
   *
   * @param host the hostname
   * @param port the port
   * @param path the request path
   * @return a formatted URI String
   */
  private static String getFormatedUri(String host, int port, String path) {
    return String.format("https://%s:%s%s", host, port, path);
  }

}
