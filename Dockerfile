FROM openjdk:11-jre-slim
COPY target/wcapp-0.0.1-SNAPSHOT.jar /app/wcapp.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/wcapp.jar"]