# logixs-challenge
Este proyecto es un code challenge para Logixs.
## About the project
El proyecto trata sobre una solución que permita gestionar cursos y estudiantes. Está compuesto por dos microservicios
conectados a través de un API Gateway usando Eureka para el descubrimiento entre las distintas capas montado en Docker.
Los endpoints generados en los microservicios son los siguientes:
#### estudiantes-ms
```
- POST /estudiantes + @RequestBody EstudianteDTO
- GET /estudiantes/{estudianteId}
- PUT /estudiantes/{estudianteId} + @RequestBody EstudianteDTO
- DELETE /estudiantes/{estudianteId}
- GET /estudiantes
```
#### cursos-ms
```
- POST /cursos + @RequestBody CursoDTO
- GET /cursos/{cursoId}
- PUT /cursos/{cursoId} + @RequestBody CursoDTO
- DELETE /cursos/{cursoId}
- GET /cursos
- POST /cursos/{cursoId}/inscribir/{estudianteId}
- POST /cursos/{cursoId}/desinscribir/{estudianteId}
```
A través de las siguientes url se podrá acceder a swagger y poder lanzar los diferentes métodos de la aplicación:

- http://localhost:8081/swagger-ui/index.html#/ (estudiantes-ms)
- http://localhost:8080/swagger-ui/index.html#/ (cursos-ms)

## Building and running the project
El proyecto ha sido construido con SpringBoot 3.1.4, Java 17, Maven 3.6.3 y PostgreSQL.
Para lanzarlo se deben ejecutar los siguientes comandos desde la ruta de /logixs-challenge, lo que construirá todos los componentes
y los ejecutará.
```
mvn clean install
docker-compose up
```
## Finishing the project
Para la finalización de la prueba quedaría incluir Keycloak para la autenticación y autorización de acceso a los aplicativos y sus 
correspondientes endpoints y de esta manera aplicar una capa de seguridad.

