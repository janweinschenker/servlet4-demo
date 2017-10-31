module de.holisticon.servlet4demo.servertomcat {

  requires de.holisticon.servlet4demo;

  requires java.management;

  requires spring.beans;
  requires spring.context;
  requires spring.web;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires tomcat.embed.core;

  exports de.holisticon.servlet4demo.servertomcat;
}