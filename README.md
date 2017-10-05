[![Build Status](https://travis-ci.org/janweinschenker/servlet4-demo.svg?branch=master)](https://travis-ci.org/janweinschenker/servlet4-demo)
[![Coverage Status](https://coveralls.io/repos/github/janweinschenker/servlet4-demo/badge.svg?branch=master)](https://coveralls.io/github/janweinschenker/servlet4-demo?branch=master)


# How to ...

## Build this project

```
you@there:~/servlet4-demo$ mvn clean install
```

## Run the server

This script will startup the server:
```
you@there:~/servlet4-demo/spring-boot$ ./script/run.sh
```

## Query the server

1. install nghttp
1. run:
   ```
   you@there:~/servlet4-demo/spring-boot$ nghttp -v https://localhost:8444/greeting
   ```
1. Read the response. You will notice, that the server send you an initial response as well as an http push.