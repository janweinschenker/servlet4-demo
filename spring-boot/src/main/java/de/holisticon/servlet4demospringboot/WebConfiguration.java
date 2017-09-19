package de.holisticon.servlet4demospringboot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = WebConfiguration.class)
public class WebConfiguration implements WebMvcConfigurer {

  public static final int CACHE_PERIOD_ROUGHLY_A_YEAR = 31556926;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    WebContentInterceptor interceptor = new WebContentInterceptor();
    interceptor.addCacheMapping(CacheControl.maxAge(365, TimeUnit.DAYS), "/**");
    registry.addInterceptor(interceptor);
  }


}