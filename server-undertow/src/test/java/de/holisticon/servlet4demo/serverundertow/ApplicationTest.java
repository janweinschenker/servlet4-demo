package de.holisticon.servlet4demo.serverundertow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.xnio.Option;

import io.undertow.Undertow;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ApplicationTest {

  private Application sut;
  private Undertow.Builder builder;

  @Autowired
  private GreetingController greetingController;

  @BeforeEach
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
