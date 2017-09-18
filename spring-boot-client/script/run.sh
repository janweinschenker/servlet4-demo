#!/bin/bash
java -Xbootclasspath/p:lib/alpn-boot-8.1.5.v20150921.jar  \
    -jar target/servlet4-demo-spring-boot-client-0.0.1-SNAPSHOT.jar  \
    --debug \

