module de.holisticon.servlet4demospringbootclient {

  requires spring.core;
  requires spring.expression;
  requires spring.boot;
  requires spring.boot.autoconfigure;

  requires okhttp;
  requires jackson.annotations;
  requires log4j.over.slf4j;
  requires spring.beans;
  requires spring.context;
  requires  spring.web;



  exports de.holisticon.servlet4demojettyclient;
}