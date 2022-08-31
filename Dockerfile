#FROM registry.redhat.io/ubi8/openjdk-8-runtime
#FROM registry.redhat.io/ubi8/openjdk-11-runtime
FROM registry.access.redhat.com/ubi8/openjdk-11-runtime
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/home/jboss/app.jar"]