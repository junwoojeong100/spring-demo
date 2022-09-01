FROM registry.redhat.io/ubi8/openjdk-11-runtime:1.14-3
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/home/jboss/app.jar"]
#test12345