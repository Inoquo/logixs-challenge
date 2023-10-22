FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} courses-ms.jar
EXPOSE 8080
CMD ["java", "-jar", "courses-ms.jar"]
