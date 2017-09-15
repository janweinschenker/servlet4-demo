#!/bin/bash
httpversion=$1
serverport=$2
java -Xbootclasspath/p:lib/alpn-boot-8.1.5.v20150921.jar  \
    -jar target/servlet4-demo-spring-boot-0.0.1-SNAPSHOT.jar  \
    --http.version=$httpversion --server.port=$serverport \
    --debug
