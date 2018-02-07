module de.holisticon.servlet4demo.hc {
  requires spring.beans;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.context;
  requires httpclient5;
  requires httpcore5;
  requires httpcore5.h2;
  requires slf4j.api;
  requires assertj.core;


  exports de.holisticon.servlet4demo.hc;
}
