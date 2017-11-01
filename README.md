[![Build Status](https://travis-ci.org/janweinschenker/servlet4-demo.svg?branch=master)](https://travis-ci.org/janweinschenker/servlet4-demo)
[![Coverage Status](https://coveralls.io/repos/github/janweinschenker/servlet4-demo/badge.svg?branch=master)](https://coveralls.io/github/janweinschenker/servlet4-demo?branch=master)

# Servlet 4 Demo 

This demo project is meant to show how to implement http2 servers and clients with Spring Boot 2.0.0M5 and Glassfish 5. All Implementation should utilize
the Servlet4 library of JavaEE8.

The demo is a  Maven mulit-module project that consists of these modules:

| Module | Description |
| --- | --- |
| [client-jetty](client-jetty/README.md) | An http2 client implemented with Jetty's http client.|
| [client-okhttp](client-okhttp/README.md) | An http2 client implemented with spring boot 2 and OkHttp's http client. |
| client-httpcomponents| An http2 client implemented with Apache HTTP components. This one will be made available as soon as [version 5.0](https://hc.apache.org/httpcomponents-client-5.0.x/index.html) of this library has been released.|
| [server-glassfish](server-glassfish/README.md)| An http2 servlet that runs on a Glassfish 5 application server. Will use local port 8181 for http2.|
| [server-jetty](server-jetty/README.md) | An http2 rest controller that is implemented with spring boot 2 and Jetty's embedded http server. Will use local port 8447 for http2 and 8087 for http 1.1.|
| [server-tomcat](server-tomcat/README.md) | An http2 rest controller that is implemented with spring boot 2 and Tomcat's embedded http server. Will use local port 8449 for http2.|
| [server-undertow](server-undertow/README.md) | An http2 rest controller that is implemented with spring boot 2 and Undertows's embedded http server. Will use local port 8448 for http2.|
| [demo-util](demo-util/README.md) | Common and utility stuff. |

## Requirements: needs Java 9

The following requirements must be met by your development environment:

* Maven 3.3.9+
* Java Development Kit 9
  * JAVA_HOME environment variable must be set to point to a JDK 9 installation.
* Docker - required to run the Glassfish server.
* Import the demo servers' TLS certificates into your local Java keystore.
  * Run this shell script:
  * `./server-jetty/script/import-server-cert.sh`


## Build this project

```
you@there:~/servlet4-demo$ mvn clean install
```

If you want to build this project without creating the docker container from module [server-glassfish](server-glassfish/README.md), use the **no-docker** profile:

```
you@there:~/servlet4-demo$ mvn clean install -P no-docker
```

## Run the demos

Please refer to the readme files of the modules.

