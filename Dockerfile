FROM openjdk:8-jdk
COPY target/*.jar  /opt/demo.jar
COPY src/main/resources/application.properties /opt/application.properties
EXPOSE 7080
CMD /opt/demo.jar