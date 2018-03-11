#!/bin/bash
docker run --name glassfish-servlet4 -it --rm -d -p 8181:8182 weinschenker/glassfish-servlet4:1.0.5-SNAPSHOT
