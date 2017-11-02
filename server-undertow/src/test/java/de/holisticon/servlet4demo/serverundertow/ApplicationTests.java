package de.holisticon.servlet4demo.serverundertow;

import io.undertow.Undertow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.xnio.Option;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

  private Application sut;
  private  Undertow.Builder builder;

  @Before
  public void setUp() {
    sut = new Application();
    builder = mock(Undertow.Builder.class);
    when(builder.setServerOption(any(Option.class), any())).thenReturn(builder);
  }

  @Test
  public void contextLoads() {
  }

  @Test
  public void testCustomize() {
    sut.customize(builder);
    verify(builder, times(2)).setServerOption(any(Option.class), any());
    verify(builder, times(1)).addHttpListener(0, "0.0.0.0");
  }

}
