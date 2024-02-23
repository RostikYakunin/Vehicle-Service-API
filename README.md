# README file in progress !!!

## Vehicle service
This project represents "Vehicle service" is a Java application developed using the Spring Boot framework and
Gradle as the build tool. The primary goal of the project is to demonstrate the integration of Vehicle Service API 
functionality. Functionality is divided on three layers which interacted with each other: Driver service, Route service 
and Transport service. 

**Key Features**
- *Authentication:* The project implements a robust authentication system, allowing users to log in securely.

- *Security* The project is secured by jwt authorization, so if you want to use it you need a token

- *Data Manipulation:* Users can do all CRUD operations with entities using appropriate endpoints.

### Technologies Used

**The project is built with the following major frameworks, technologies and tools:**

- *Spring Boot:* A powerful and convention-over-configuration framework for Java applications, with modules: Spring Data, 
   Spring Validation, Spring Security and other.

- *Open API:* Tool can use to generate code, documentation, test cases, and more for your API.

- *Gradle:* A modern, versatile build tool for Java and Groovy projects.

- *PostgreSQL:* A SQL database used for storing application data.

- *pgAdmin:* Is the most popular and feature rich Open Source administration and development platform for PostgreSQL

- *JUnit and Mockito Framework:* Testing and specification frameworks for Java applications.

- *Flyway:* An open-source database-migration tool.

- *Docker:* An open source software platform used to create, deploy and manage virtualized application containers on 
   a common operating system (OS), with an ecosystem of allied tools.

- *Jenkins:* An open source continuous integration (CI) server. It manages and controls several stages of the software 
   delivery process, including build, documentation, automated testing, packaging, and static code analysis.

### Get started

### Install docker and docker compose for your platform:
- #### [Linux](https://docs.docker.com/desktop/install/linux-install/)
- #### [MacOS](https://docs.docker.com/desktop/install/mac-install/)
- #### [Windows](https://docs.docker.com/desktop/windows/wsl/)

### Repo installation
- Clone the repo
   ```sh
   git clone https://github.com/RostikYakunin/Vehicle-Service-API
   ```

### General container usage
- #### [DOCKER COMPOSE DOCUMENTATION](https://docs.docker.com/compose/reference/)
- To run use only: `"docker compose up"`, all logs will be streamed in terminal. <br>
  To stop all containers press: `"CTRL+C"`.

> NOTE: Database saves it state between runs.

### Config
- App configs are saved in `"src/main/resources/application.yaml"`
- Environment variables are saved in `".env"`

### Authentication and Token Management
This section describes how to register, authenticate, and refresh tokens in the app using the provided REST API endpoints.

Additionally, you can read the Open API documentation here: http://localhost:8080/swagger-ui/index.html

### Examples
#### 1. Register a new user using authentication
To register a new user, make a POST request to the /api/v1/auth/singup/ endpoint with a JSON payload containing the username, email, password
and other filed according to example below. It needs for token`s receiving. Your next step is to use token in "Bearer type" authentication to
get access to other functionality of the app.

```
POST /api/v1/auth/singup/ 

{
    "userName":"username",
    "firstName": "firstName",
    "lastName": "lastName",
    "email":"email@gmail.com",
    "password":"password"
}
```

#### 2. 
