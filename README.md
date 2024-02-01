# README file in progress

## Vehicle service
This project represents "Vehicle service" is a Java application developed using the Spring Boot framework and
Gradle as the build tool. The primary goal of the project is to demonstrate the integration of Vehicle Service API 
functionality. Functionality is divided on three layers which interacted with each other: Driver service, Route service 
and Transport service. 

**Key Features**
- *Authentication:* The project implements a robust authentication system, allowing users to log in securely.
- 
- *Security* The project is secured by jwt authorization, so if you want to use it you need a token

- *Data Manipulation:* Users can do all CRUD operations with entities using appropriate endpoints.

### Technologies Used

**The project is built with the following major frameworks and technologies:**

- *Spring Boot:* A powerful and convention-over-configuration framework for Java applications, with modules: Spring Data, 
   Spring Validation, Spring Security and other.

- *Gradle:* A modern, versatile build tool for Java and Groovy projects.

- *PostgreSQL:* A SQL database used for storing application data.

- *JUnit and Mockito Framework:* Testing and specification frameworks for Java applications.

- *Flyway:* Is an open-source database-migration tool.

- *Docker:* is an open source software platform used to create, deploy and manage virtualized application containers on 
   a common operating system (OS), with an ecosystem of allied tools.

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
All app configs are saved in `"src/main/resources/application.yaml"`

### Some examples

#### 1. Register a new user using authentication
To register a new user, make a POST request to the /api/v1/auth/ endpoint with a JSON payload containing the username, email, and password
```
POST /api/v1/auth/login/ 

{
    "username": "user"
    "email": "user@example.com",
    "password": "your_password",
}
```

#### 2. Create a new user manually
To register a new user, make a POST request to the /api/v1/users/ endpoint with a JSON payload containing the username, email, and password
```
POST /api/v1/users/ 

{
    "username": "user"
    "email": "user@example.com",
    "password": "your_password",
}
```

#### 2. Find post by id
To find a post, make a GET request to the /api/v1/posts/ endpoint and type path variable '{id}' (post`s id)
```
GET /api/v1/posts/{id} 
```

If post with this id is exists, you will receive information in JSON format like this:

```
{
"id": "string_value",
"content": "content_value",
"authorId": "authorId_value",
"likesId": [],
"commentsId": []
}
```