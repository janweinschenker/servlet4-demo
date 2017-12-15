package de.holisticon.servlet4demo.jettyclient.jetty;

import org.eclipse.jetty.http2.client.HTTP2Client;

@FunctionalInterface
public interface ClientInitializer {

  void init();

}
