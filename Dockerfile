#FROM maven AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8300
ADD out/artifacts/THC_jar/THC.jar thc.jar
#ADD target/THC-1.0-SNAPSHOT.jar thc.jar
ENTRYPOINT ["java","-jar","/thc.jar"]
