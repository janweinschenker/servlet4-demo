package de.holisticon.servlet4demo.serverjetty;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

public class ApplicationConfigTest {

  private ApplicationConfig sut;

  private InterceptorRegistry registry;

  private Server server;

  @Before
  public void setUp() {
    sut = new ApplicationConfig();
    server = mock(Server.class);
    registry = mock(InterceptorRegistry.class);
    when(server.getThreadPool()).thenReturn(new ThreadPool() {
      @Override
      public void join() throws InterruptedException {

      }

      @Override
      public int getThreads() {
        return 0;
      }

      @Override
      public int getIdleThreads() {
        return 0;
      }

      @Override
      public boolean isLowOnThreads() {
        return false;
      }

      @Override
      public void execute(Runnable command) {

      }
    });
  }

  @Test
  public void testAddInterceptors() {
    sut.addInterceptors(registry);
    verify(registry, times(1)).addInterceptor(any(WebContentInterceptor.class));
  }

  @Test
  public void testConfigureServerForHttp2() {
    sut.configureServerForHttp2(server);
    verify(server, times(1)).addConnector(any(ServerConnector.class));

  }
}


