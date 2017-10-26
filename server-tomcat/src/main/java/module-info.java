module de.holisticon.servlet4demo.servertomcat {

  requires de.holisticon.servlet4demo;

  requires java.management;

  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.aop;
  requires spring.web;
  requires spring.expression;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.webmvc;

  requires jackson.annotations;
  requires log4j.over.slf4j;
  requires tomcat.embed.core;

  exports de.holisticon.servlet4demo.servertomcat;
}