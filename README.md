# logixs-challenge
This project is a code challenge for Logixs.
## About the project
The project is about a solution to manage courses and students. It is composed of two microservices
connected through an API Gateway using Eureka for the discovery between the different layers mounted on Docker.
The endpoints generated in the microservices are the following:
#### students-ms
```
- POST /students + @RequestBody StudentDTO
- GET /students/{studentId}
- PUT /students/{studentId} + @RequestBody StudentDTO
- DELETE /students/{studentId}
- GET /students
```
#### courses-ms
```
- POST /courses + @RequestBody CourseDTO
- GET /courses/{courseId}
- PUT /courses/{courseId} + @RequestBody CourseDTO
- DELETE /courses/{courseId}
- GET /courses
- POST /courses/{courseId}/enrol/{studentId}
- POST /courses/{courseId}/unenrol/{studentId}
```
Through the following url you can access swagger and visualise the documentation of the microservices:

- http://localhost:8081/swagger-ui/index.html#/ (students-ms)
- http://localhost:8080/swagger-ui/index.html#/ (courses-ms)

## Building and running the project
The project has been built with SpringBoot 3.1.4, Java 17, Maven 3.6.3 and PostgreSQL.
To launch it you must execute the following commands from the /logixs-challenge path, which will build all the components and run them.
```
mvn clean install
docker-compose up
```
## Finishing the project
For the completion of the challenge, it would be necessary to include Keycloak for authentication and authorisation of access to the applications and their corresponding endpoints, thus applying a layer of security.
and their corresponding endpoints and thus apply a layer of security.

