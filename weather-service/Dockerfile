FROM openjdk:11-jre-slim
COPY target/weather-service-0.0.1-SNAPSHOT.jar /app/weather.jar
EXPOSE 8082
CMD ["java","-jar","/app/weather.jar"]