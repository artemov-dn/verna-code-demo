FROM openjdk:8-jre-alpine
WORKDIR /home/demo
COPY target/*.jar  demo.jar
EXPOSE 7080
ENTRYPOINT ["java","-jar","demo.jar"]
