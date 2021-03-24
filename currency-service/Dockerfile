FROM openjdk:11-jre-slim
COPY target/currency-service-0.0.1-SNAPSHOT.jar /app/currency.jar
EXPOSE 8081
CMD ["java","-jar","/app/currency.jar"]