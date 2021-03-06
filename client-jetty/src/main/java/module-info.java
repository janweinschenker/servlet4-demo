@SuppressWarnings({"deprecation", "removal"})
    module de.holisticon.servlet4demo.jettyclient {

  requires de.holisticon.servlet4demo.util;

  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.aop;
  requires spring.web;
  requires spring.expression;
  requires spring.boot;
  requires spring.boot.autoconfigure;

  requires http2.client;
  requires http2.http.client.transport;
  requires http2.common;
  requires jetty.client;
  requires jetty.http;
  requires jetty.util;
  requires jackson.annotations;
  requires slf4j.api;

  exports de.holisticon.servlet4demo.jettyclient;
}