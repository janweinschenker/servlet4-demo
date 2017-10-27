# An http2 server built with Glassfish

This module shows how to build Web Services with the Glassfish application server that are capable to speak http2.

## Run this demo

1. Run `$ mvn clean install`
   * This will compile and package the Java code. Furthermore, it will build
    the docker image that contains the running Glassfish server.
1. Execute the script [./docker/run.sh](./docker/run.sh) to start the docker container. If you have a Windows environment,
use [./docker/run.cmd](./docker/run.cmd)
1. The server will start and be available at [https://localhost:8181/Servlet4Push/http2](https://localhost:8181/Servlet4Push/http2)
    * The default https port number is 8181. You can configure the exposed port in the [Dockerfile](./Dockerfile)

# Some important code snippets

## The [Dockerfile](./Dockerfile)

Obviously, this is important to build our docker image. The image will include the servlet war
file from the target directory of this module.

## Class [de.holisticon.servlet4demo.serverglassfish.Http2Servlet](src/main/java/de/holisticon/servlet4demo/serverglassfish/Http2Servlet.java)

Our Servlet that implements version 4 of the servlet api. It is mapped to the URL [https://localhost:8181/Servlet4Push/http2](https://localhost:8181/Servlet4Push/http2).

When this link is opened with a web browser, the server will serve a small HTML snippet and it will issue a push promise for an image of a vicious cat.

Additionally, a second push promise is offered. The second one promises a JSON response from a second servlet, that is implemented with the class
[de.holisticon.servlet4demo.serverglassfish.Http2JsonServlet](src/main/java/de/holisticon/servlet4demo/serverglassfish/Http2JsonServlet.java)