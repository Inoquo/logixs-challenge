FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} students-ms.jar
EXPOSE 8081
CMD ["java", "-jar", "students-ms.jar"]
