# Use Maven to build the application, then run tests
FROM maven:3.8.6-openjdk-17-slim as builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and src to the container
COPY pom.xml .
COPY src /app/src

# Install dependencies and run tests
RUN mvn clean install

# Use OpenJDK image for the runtime
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build container
COPY --from=builder /app/target/employeemanager-0.0.1-SNAPSHOT.jar employeemanager-app.jar

# Expose port 8080 (default Spring Boot port)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "employeemanager-app.jar"]
