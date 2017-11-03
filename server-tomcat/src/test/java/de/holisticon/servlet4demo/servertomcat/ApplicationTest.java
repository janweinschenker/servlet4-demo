package de.holisticon.servlet4demo.servertomcat;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http2.Http2Protocol;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

/**
 * Unit test for simple App.
 */
public class ApplicationTest {

  private Application sut;

  private Connector connector;

  @Before
  public void setUp() {
    sut = new Application();
    connector = mock(Connector.class);
  }

  @Test
  public void testTomcatCustomizer() {
    TomcatServletWebServerFactory factory = sut.tomcatCustomizer();
    List<TomcatConnectorCustomizer> tomcatConnectorCustomizers = new ArrayList(factory.getTomcatConnectorCustomizers());
    assertTrue(tomcatConnectorCustomizers.size() == 1);
  }

  @Test
  public void testCustomize() {
    TomcatServletWebServerFactory factory = sut.tomcatCustomizer();
    List<TomcatConnectorCustomizer> tomcatConnectorCustomizers = new ArrayList(factory.getTomcatConnectorCustomizers());
    TomcatConnectorCustomizer customizer = tomcatConnectorCustomizers.get(0);

    assertNotNull(customizer);
    customizer.customize(connector);
    verify(connector, times(1)).addUpgradeProtocol(any(Http2Protocol.class));
  }


}
