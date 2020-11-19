#!/bin/sh
mvn clean package && docker build -t ims/m-users .
docker rm -f m-users || true && docker run --rm -d -p 8081:8080 --name m-users ims/m-users
