package de.holisticon.servlet4demo.jettyclient.jetty;

import java.nio.ByteBuffer;

import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.util.BufferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentListener implements Response.ContentListener {

  public static final Logger LOG = LoggerFactory.getLogger(ContentListener.class);

  @Override
  public void onContent(Response response, ByteBuffer byteBuffer) {
    LOG.info("content: " + BufferUtil.toString(byteBuffer));
    LOG.info("");
  }
}
