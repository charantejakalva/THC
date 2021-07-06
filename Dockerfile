#FROM maven AS build
#COPY src /home/app/src
#COPY pom.xml /home/app
#RUN mvn -f /home/app/pom.xml clean package

FROM openjdk
VOLUME /tmp
EXPOSE 8300
ADD target/libs/jarfile.jar thc.jar
ENTRYPOINT ["java","-jar","/thc.jar"]
