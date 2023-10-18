FROM adoptopenjdk:17-jre
WORKDIR /app
COPY target/api-gateway.jar api-gateway.jar
EXPOSE 8765
CMD ["java", "-jar", "api-gateway.jar"]
