package de.holisticon.servlet4demo.jettyclient.jetty;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.util.BufferUtil;

public class ContentListener implements Response.ContentListener {

  public static final Logger LOG = Logger.getLogger(ContentListener.class);

  @Override
  public void onContent(Response response, ByteBuffer byteBuffer) {
    LOG.info("content: " + BufferUtil.toString(byteBuffer));
    LOG.info("");
  }
}
