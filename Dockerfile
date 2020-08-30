#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
#COPY pom.xml /build/
#COPY src /build/src/
#WORKDIR /build/
#RUN mvn package

FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/pushnotification.jar
CMD ["java","-jar","/app/pushnotification.jar"]
