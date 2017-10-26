# An http2 client built with Spring Boot and OkHttp

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

# Some important code snippets

## Class [de.holisticon.servlet4demo.okhttpclient.ApplicationConfig](src/main/java/de/holisticon/servlet4demo/okhttpclient/ApplicationConfig.java)
This class provides a Spring bean for a `RestTemplate` that is initialized with an OkHttp client factory.

OkHttp is capable of communicating over http/2 without much additional configuration. The only thing to keep in mind is, as mentioned above, that you have to add
your server's self signed TLS certificate to the JDK's keystore. 

## Class [de.holisticon.servlet4demo.okhttpclient.Application](src/main/java/de/holisticon/servlet4demo/okhttpclient/Application.java)
This class obtains an instance of the `RestTemplate` we created in our `ApplicationConfig`.

This instance is used to contact tomcat server we started earlier.

The log level of the OkHttp client is set to `DEBUG` in order to display the HTTP communication in detail.

    ========================= restTemplate okHttp client => tomcat server ==============
    
    >> CONNECTION 505249202a20485454502f322e300d0a0d0a534d0d0a0d0a

       STREAM_ID        FRAME TYPE    FLAGS
    >> 0x00000000     6 SETTINGS      
    >> 0x00000000     4 WINDOW_UPDATE 
    << 0x00000000     6 SETTINGS      
    >> 0x00000003    81 HEADERS       END_STREAM|END_HEADERS
    << 0x00000000     0 SETTINGS      ACK
    << 0x00000000     8 PING          
    >> 0x00000000     0 SETTINGS      ACK
    >> 0x00000000     8 PING          ACK
    << 0x00000003   126 PUSH_PROMISE  END_PUSH_PROMISE
    >> 0x00000002     4 RST_STREAM    
    << 0x00000003    53 HEADERS       END_HEADERS
    << 0x00000003    37 DATA          
    << 0x00000003     0 DATA          END_STREAM
    << 0x00000000   104 GOAWAY        
    
    Greetings, Hello, JavaLand! (1)
    ====================================================================================
    Started Application in 1.691 seconds (JVM running for 5.518)