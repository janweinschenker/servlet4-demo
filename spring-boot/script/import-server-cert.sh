#!/bin/bash

keytool -import -v -trustcacerts -alias localhost-alias3 -file localhost.crt -keystore $JAVA_HOME/jre/lib/security/cacerts -keypass changeit -storepass changeit
