module de.holisticon.servlet4demo.serverjetty {

  requires de.holisticon.servlet4demo.util;

  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.aop;
  requires spring.web;
  requires spring.expression;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.webmvc;

  requires jetty.alpn.server;
  requires jetty.http;
  requires jetty.server;
  requires jetty.util;
  requires http2.common;
  requires http2.server;
  requires jackson.annotations;
  requires javax.servlet.api;
  requires log4j.over.slf4j;
  requires assertj.core;

  exports de.holisticon.servlet4demo.serverjetty;
}