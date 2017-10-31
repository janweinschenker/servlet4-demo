module de.holisticon.servlet4demo.serverundertow {

  requires de.holisticon.servlet4demo;

  requires java.management;
  requires javax.servlet.api;

  requires spring.beans;
  requires spring.context;
  requires spring.web;
  requires spring.boot;
  requires spring.boot.autoconfigure;

  requires log4j.over.slf4j;
  requires undertow.core;
  requires undertow.servlet;

  exports de.holisticon.servlet4demo.serverundertow;
}