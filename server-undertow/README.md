# An http2 server built with Spring Boot and an embedded Undertow server

This module shows how to create a Spring REST controller that is capable to speak http2.

## Run this demo

1. Run `$ mvn clean install spring-boot:run`
1. The server will start and be available at [https://localhost:8449/greeting](https://localhost:8449/greeting)
    * The port number 8449 is configured in the [root pom](../pom.xml).
     
# Some important code snippets

## Class [de.holisticon.servlet4demo.serverundertow.Application](src/main/java/de/holisticon/servlet4demo/serverundertow/Application.java)

This class creates an instance of a `UndertowServletWebServerFactory`. We do this in order to configure our server to
speak http2.
