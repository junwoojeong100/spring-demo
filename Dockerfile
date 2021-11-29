#FROM docker.io/adoptopenjdk/openjdk11:alpine-jre
FROM ubi8/openjdk-11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
