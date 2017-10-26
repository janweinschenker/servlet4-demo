# An http2 server built with Spring Boot and an embedded Tomcat

This module shows how to create a Spring REST controller that is capable to speak http2.

## Run this demo

1. Run `$ mvn clean install spring-boot:run`
1. The server will start and be available at [https://localhost:8449/greeting](https://localhost:8449/greeting)
    * The port number 8449 is configured in the [root pom](../pom.xml).
    
# Some important code snippets

## Class [de.holisticon.servlet4demo.servertomcat.Application](src/main/java/de/holisticon/servlet4demo/servertomcat/Application.java)

This class creates an instance of a `TomcatServletWebServerFactory`. We do this in order to configure our server to
speak http2 by adding an appropriate http2 connector.

## Class [de.holisticon.servlet4demo.servertomcat.GreetingController](src/main/java/de/holisticon/servlet4demo/servertomcat/GreetingController.java)

This class is a mostly plain vanilla Spring Boot REST controller that defines two request mappings.

1. The first mapping handles requests for [https://localhost:8449/greeting](https://localhost:8449/greeting).
The method that handles this request also creates a push promise, thatf will offer to send the push-greeting from the next mapping to the client.
1. The seconds mapping handles requests for [https://localhost:8449/push-greeting](https://localhost:8449/push-greeting).