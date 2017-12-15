module de.holisticon.servlet4demo.serverundertow {

  requires de.holisticon.servlet4demo.util;

  requires java.management;
  requires javax.servlet.api;

  requires spring.beans;
  requires spring.context;
  requires spring.web;
  requires spring.boot;
  requires spring.boot.autoconfigure;

  requires assertj.core;
  requires undertow.core;
  requires undertow.servlet;
  requires slf4j.api;

  exports de.holisticon.servlet4demo.serverundertow;
}