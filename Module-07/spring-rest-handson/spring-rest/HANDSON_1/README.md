# Spring REST using Spring Boot 3 - Hands On 1

1.Create a Spring Web Project using Maven

## Objective
To create a Spring Web Project using Maven and understand the project structure, Spring Boot application startup process, Maven configuration, and dependency management.

## Project Creation Steps

1. Open Spring Initializr: https://start.spring.io/
2. Set Group as `com.cognizant`
3. Set Artifact as `spring-learn`
4. Add dependencies:
   - Spring Boot DevTools
   - Spring Web
5. Generate and download the project.
6. Extract the project.
7. Build using Maven.
8. Import as Existing Maven Project.
9. Add logs in main().
10. Run SpringLearnApplication.

## Project Structure

spring-learn
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.cognizant.spring_learn
│   │   │       ├── SpringLearnApplication.java
│   │   │       ├── controller
│   │   │       ├── service
│   │   │       └── model
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java
│           └── com.cognizant.spring_learn
│               └── SpringLearnApplicationTests.java
│
├── pom.xml
└── README.md

## src/main/java
Contains application source code including Controllers, Services, Models and the main Spring Boot application class.

## src/main/resources
Contains configuration files such as application.properties and static resources.

Example:

src/main/java
└── com.cognizant.spring_learn
    ├── SpringLearnApplication.java
    ├── controller
    ├── service
    └── model

src/main/resources

The src/main/resources folder contains application configuration files and resources.

Common files:

application.properties
application.yml
static files
templates

Example:

server.port=8083

src/test/java

The src/test/java folder contains test cases.

Purpose:

Unit Testing
Integration Testing
Controller Testing
Service Testing

Example:

@SpringBootTest
class SpringLearnApplicationTests {

    @Test
    void contextLoads() {

    }
}

## SpringLearnApplication.java

package com.cognizant.spring_learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringLearnApplication {

    public static void main(String[] args) {

        System.out.println("Application Started");

        SpringApplication.run(
                SpringLearnApplication.class,
                args);

        System.out.println("Application Running");
    }
}


### main() Method
- Entry point of application
- Starts Spring Container
- Loads configurations
- Starts embedded Tomcat
- Handles HTTP requests

## Purpose of @SpringBootApplication

Combines:
- @Configuration
- @EnableAutoConfiguration
- @ComponentScan

Benefits:
- Automatic configuration
- Component scanning
- Faster development

## pom.xml

Purpose:
- Dependency management
- Build management
- Plugin configuration

### Parent

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
</parent>
```

### Dependencies

#### Spring Web

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

Provides:
- REST APIs
- Spring MVC
- Embedded Tomcat

#### Spring Boot DevTools

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

Provides:
- Auto restart
- Live reload

## Dependency Hierarchy

```text
spring-boot-starter-web
├── spring-web
├── spring-webmvc
├── spring-core
├── spring-context
├── jackson-databind
└── tomcat-embed-core
```

## Maven Build

```bash
mvn clean package
```

Expected:

```text
BUILD SUCCESS
```

## Running the Application

```bash
mvn spring-boot:run
```

Expected:

```text
Tomcat started on port(s): 8083
Started SpringLearnApplication
```

## Conclusion

Successfully created a Spring Web Project using Maven and understood Spring Boot project structure, Maven configuration, dependency management, and application startup.
