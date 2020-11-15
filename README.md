# Issue-Management-System

#### This system will be used for managing issues that are raised as tickets and get assigned to users for resolution.

Functional services: ims-micro-users, ims-micro-tasks, ims-micro-notify;

The projects are created as standard Java EE projects, which are Skinny WARs, that will be deployed using the Payara Micro server.

#### You can start Payara Micro with this services deployed locally:
Ex: java -jar payara-micro-5.2020.6.jar --deploy C:/Users/RAMONA/Desktop/Microservices/ims/ims-micro-users/target/ims-micro-users-0.0.1-SNAPSHOT.war --port 8080

#### This will start instances of Payara Micro running on the specified ports
Ex: http://localhost:8080/ims-micro-users/resources/users/

### Uber JAR option
Ex: java -jar payara-micro-5.2020.6.jar --deploy C:/Users/RAMONA/Desktop/Microservices/ims/ims-micro-users/target/ims-micro-users-0.0.1-SNAPSHOT.war --outputUberJar users.jar

-> This will generate the users.jar file in the directory where you run this command.

#### Here's how you can start the application using the generated JAR: java -jar users.jar
