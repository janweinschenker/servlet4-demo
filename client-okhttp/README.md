# An http2 client build with Spring Boot and OkHttp

This module shows how to issue HTTP/2 requests with Spring Boot's RestTemplate. The underlying http2-client is OkHttp.

# Run this demo

1. **Only once:**  Import the server's TLS certificate into your JDK's keystore. This project contains a bash script for this 
purpose:
   * `you@yourcomputer: ~GIT-ROOT/servlet4-demo/server-jetty/script$ import-server-cert.sh`
1. Start the tomcat server from the module project at `../server-tomcat` and cd back into client-module afterwards
      * `cd ../server-tomcat`
      * `mvn clean install spring-boot:run`
      * `cd ../client-okhttp`   
1. Now run this module project with `mvn spring-boot:run`
1. If everything works out fine, you will see that spring-boot will perform a GET request against the tomcat server.
