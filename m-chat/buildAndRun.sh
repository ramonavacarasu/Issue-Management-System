#!/bin/sh
mvn clean package && docker build -t ims/m-chat .
docker rm -f m-chat || true && docker run --rm -d -p 8084:8080 --name m-chat ims/m-chat
