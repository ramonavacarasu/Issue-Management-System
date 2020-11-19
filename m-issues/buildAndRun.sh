#!/bin/sh
mvn clean package && docker build -t ims/m-issues .
docker rm -f m-issues || true && docker run --rm -d -p 8082:8080 --name m-issues ims/m-issues
