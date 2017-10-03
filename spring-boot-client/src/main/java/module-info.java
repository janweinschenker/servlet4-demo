@SuppressWarnings({"deprecation", "removal"})
    module de.holisticon.servlet4demospringbootclient {

  requires java.se.ee;

  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.aop;
  requires spring.web;
  requires spring.expression;

  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires log4j.over.slf4j;
  requires http2.client;
  requires jetty.client;
  requires okhttp;
  requires http2.http.client.transport;
  requires jetty.util;
  requires http2.common;
  requires jetty.http;
  requires jackson.annotations;

  exports de.holisticon.servlet4demospringbootclient;
}