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
  requires okhttp;
  requires jackson.annotations;

  exports de.holisticon.servlet4demospringbootclient;
}