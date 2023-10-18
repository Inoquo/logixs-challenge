FROM openjdk:17-oracle
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} estudiantes-ms.jar
EXPOSE 8081
CMD ["java", "-jar", "estudiantes-ms.jar"]
