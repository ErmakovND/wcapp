FROM maven:3-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean install

FROM openjdk:11-jre-slim
COPY --from=build /app/target/wcapp-0.0.1-SNAPSHOT.jar /app/wcapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/wcapp.jar"]