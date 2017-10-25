[![Build Status](https://travis-ci.org/janweinschenker/servlet4-demo.svg?branch=master)](https://travis-ci.org/janweinschenker/servlet4-demo)
[![Coverage Status](https://coveralls.io/repos/github/janweinschenker/servlet4-demo/badge.svg?branch=master)](https://coveralls.io/github/janweinschenker/servlet4-demo?branch=master)
[![CircleCI](https://circleci.com/gh/janweinschenker/servlet4-demo/tree/master.svg?style=svg)](https://circleci.com/gh/janweinschenker/servlet4-demo/tree/master)

# Servlet 4 Demo 

This demo project is meant to show how to implement http2 servers and clients with Spring Boot 2.0.0M5 and Glassfish 5. All Implementation should utilize
the Servlet4 library of JavaEE8.

The demo is a  Maven mulit-module project that consists of these modules:

| Module | Description |
| --- | --- |
| [client-jetty](client-jetty/README.md) | An http2 client implemented with spring boot 2 and Jetty's http client.|
| [client-okhttp](client-okhttp/README.md) | An http2 client implemented with spring boot 2 and OkHttp's http client. |
| [server-glassfish](server-glassfish/README.md)| An http2 servlet that runs on a Glassfish 5 application server. |
| [server-jetty](server-jetty/README.md) | An http2 rest controller that is implemented with spring boot 2 and Jetty's embedded http server. |
| [server-tomcat](server-tomcat/README.md) | An http2 rest controller that is implemented with spring boot 2 and Tomcat's embedded http server. |
| [server-undertow](server-undertow/README.md) | An http2 rest controller that is implemented with spring boot 2 and Undertows's embedded http server. |

## Requirements: needs Java 9

The following requirements must be met by your development environment:

* Maven 3.3.9+
* Java Development Kit 9
  * JAVA_HOME environment variable must be set to point to a JDK 9 installation.
* Docker - required to run the Glassfish server.


## Build this project

```
you@there:~/servlet4-demo$ mvn clean install
```

## Run the demos

Please refer to the readme files of the modules.

