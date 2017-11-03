package de.holisticon.servlet4demo.serverundertow;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xnio.Option;

import io.undertow.Undertow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

  private Application sut;
  private Undertow.Builder builder;

  @Autowired
  private GreetingController greetingController;

  @Before
  @SuppressWarnings("unchecked")
  public void setUp() {
    sut = new Application();
    builder = mock(Undertow.Builder.class);
    when(builder.setServerOption(any(Option.class), any())).thenReturn(builder);
  }

  @Test
  public void contextLoads() {
    assertNotNull(greetingController);
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testCustomize() {
    sut.customize(builder);
    verify(builder, times(2)).setServerOption(any(Option.class), any());
    verify(builder, times(1)).addHttpListener(0, "0.0.0.0");
  }

}
