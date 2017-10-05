@SuppressWarnings({"deprecation", "removal"})
    module de.holisticon.servlet4demospringboot {

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
  requires http2.http.client.transport;
  requires jetty.util;
  requires http2.common;
  requires jetty.http;
  requires jackson.annotations;
  requires jetty.server;
  requires javax.servlet.api;
  requires http2.server;
  requires jetty.alpn.server;
  requires spring.webmvc;

  exports de.holisticon.servlet4demospringboot;
}