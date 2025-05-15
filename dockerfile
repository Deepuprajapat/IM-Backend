# Use official Maven image as the base image
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project file to the working directory
COPY pom.xml .

# Copy the entire source code to the working directory
COPY src ./src

# Download dependencies
RUN mvn dependency:go-offline

# Build the application
RUN mvn clean package -DskipTests

# Use OpenJDK as the base image for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the compiled application from the builder stage to the final image
COPY --from=builder /app/target/invest-0.0.1-SNAPSHOT.war .  
#/app.war

# Expose the port that the application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "invest-0.0.1-SNAPSHOT.war"]
