module de.holisticon.servlet4demo.servertomcat {

  requires java.management;

  requires spring.core;
  requires spring.beans;
  requires spring.context;
  requires spring.aop;
  requires spring.web;
  requires spring.expression;

  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires jackson.annotations;
  requires spring.webmvc;
  requires log4j.over.slf4j;
  requires tomcat.embed.core;

  exports de.holisticon.servlet4demo.servertomcat;
}