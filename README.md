# Issue-Management-System

This system will be used for managing issues that are raised as tickets and get assigned to users for resolution.

The system will facilitate a user to access the list of issues and view its details.
 
A user should also be able to create and update the issue. It will also be helpful for getting updates about various issues in the system. 

The activities done for an issue can be tracked by means of comments
added to it. 

It would also be nice to be able to chat with other users.

#### The domain model will consist of user, issue, chat message, chat thread, and comment.

### Microservices: 
#### - m-users: Services for user-specific features;
#### - m-issues: Services for task-related features which are represented as issues
#### - m-chat: Chat for bidirectional communication using WebSockets
#### - m-comments: Services for comments on issues within the system

The projects are created as standard Java EE projects, which are Skinny WARs, that will be deployed using the Payara Micro server.

## Build

#### - mvn clean package

#### - docker build -t ims/m-chat .

## RUN

#### - docker run -d -p 8080:8080 -p 4848:4848 --name m-chat ims/m-chat 


## Dockerfile

#### FROM payara/micro:5-SNAPSHOT

#### COPY ./target/m-*.war ${DEPLOY_DIR}




# Run all microservices

### docker-compose up -d

## Remove the containers 

### docker-compose down

