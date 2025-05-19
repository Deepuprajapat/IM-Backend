# Use official Maven image as the base image
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Accept build-time secrets
ARG AWSACCESSKEY
ARG AWSSECRETKEY
ARG AWSREGION
ARG AWSBUCKET

# Set environment variables for Java
ENV AWSACCESSKEY=$AWSACCESSKEY
ENV AWSSECRETKEY=$AWSSECRETKEY
ENV AWSREGION=$AWSREGION
ENV AWSBUCKET=$AWSBUCKET

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# Final image
FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/invest-0.0.1-SNAPSHOT.war .

# Pass env vars to runtime container
ARG AWSACCESSKEY
ARG AWSSECRETKEY
ARG AWSREGION
ARG AWSBUCKET

ENV AWSACCESSKEY=$AWSACCESSKEY
ENV AWSSECRETKEY=$AWSSECRETKEY
ENV AWSREGION=$AWSREGION
ENV AWSBUCKET=$AWSBUCKET

EXPOSE 8181

CMD ["java", "-jar", "invest-0.0.1-SNAPSHOT.war"]

