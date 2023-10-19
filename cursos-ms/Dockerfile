FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cursos-ms.jar
EXPOSE 8080
CMD ["java", "-jar", "cursos-ms.jar"]
