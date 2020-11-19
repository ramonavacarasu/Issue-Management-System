#!/bin/sh
mvn clean package && docker build -t ims/m-comments .
docker rm -f m-comments || true && docker run --rm -d -p 8083:8080 --name m-comments ims/m-comments
