FROM openjdk:17-oracle
#WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api-gateway.jar
EXPOSE 8765
CMD ["java", "-jar", "api-gateway.jar"]
