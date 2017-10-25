module de.holisticon.servlet4demo.okhttpclient {

  requires spring.core;
  requires spring.expression;
  requires spring.boot;
  requires spring.boot.autoconfigure;

  requires okhttp;
  requires jackson.annotations;
  requires spring.beans;
  requires spring.context;
  requires  spring.web;
  requires log4j.over.slf4j;


  exports de.holisticon.servlet4demo.okhttpclient;
}