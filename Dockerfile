# Use the official OpenJDK image as the base image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from your local system to the container
COPY target/employeemanager-0.0.1-SNAPSHOT.jar employeemanager-app.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "employeemanager-app.jar"]
