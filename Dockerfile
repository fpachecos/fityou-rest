FROM openjdk:20-jdk-oracle
COPY target/rest.jar rest.jar
ENTRYPOINT ["java","-jar","/rest.jar"]