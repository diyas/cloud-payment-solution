FROM openjdk:8

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app/pushnotification.jar

CMD ["java","-jar","/app/pushnotification.jar"]
