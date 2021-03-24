FROM openjdk:11-jre-slim
COPY target/predict-service-0.0.1-SNAPSHOT.jar /app/predict.jar
EXPOSE 8080
CMD ["java","-jar","/app/predict.jar"]