#!/bin/bash
cp ../target/Servlet4Push.war ./Servlet4Push.war
docker build -t weinschenker/glassfish-servlet4 .
rm ./Servlet4Push.war