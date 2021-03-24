FROM openjdk:11-jre-slim
COPY target/eureka-server-0.0.1-SNAPSHOT.jar /app/eureka.jar
EXPOSE 8761
CMD ["java","-jar","/app/eureka.jar"]