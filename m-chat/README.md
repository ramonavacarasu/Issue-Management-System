# Build
mvn clean package && docker build -t ims/m-chat .

# RUN

docker rm -f m-chat || true && docker run -d -p 8084:8080 -p 4848:4848 --name m-chat ims/m-chat 