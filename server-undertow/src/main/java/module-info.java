module de.holisticon.servlet4demo.serverundertow {

  requires java.management;
  requires javax.servlet.api;

  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.aop;
  requires spring.web;
  requires spring.expression;
  requires spring.webmvc;
  requires spring.boot;
  requires spring.boot.autoconfigure;

  requires jackson.annotations;
  requires log4j.over.slf4j;
  requires undertow.core;


  exports de.holisticon.servlet4demo.serverundertow;
}