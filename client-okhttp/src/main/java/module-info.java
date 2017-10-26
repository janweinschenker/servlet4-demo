module de.holisticon.servlet4demo.okhttpclient {

  requires de.holisticon.servlet4demo;

  requires spring.core;
  requires spring.expression;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.beans;
  requires spring.context;
  requires spring.web;

  requires okhttp;
  requires jackson.annotations;
  requires log4j.over.slf4j;


  exports de.holisticon.servlet4demo.okhttpclient;
}