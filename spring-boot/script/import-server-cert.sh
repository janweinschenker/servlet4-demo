#!/bin/bash

keytool -import -v -trustcacerts -alias localhost-alias4 -file localhost-glassfish.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -keypass changeit -storepass changeit
keytool -import -v -trustcacerts -alias localhost-alias3 -file localhost.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -keypass changeit -storepass changeit

# keytool -import -v -trustcacerts -alias localhost-alias4 -file localhost-glassfish.crt -keystore ../../spring-boot-client/src/main/resources/sample.jks -keypass secret -storepass secret