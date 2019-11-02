# Notes Application: (https://www.techgeeknext.com/java/spring-boot-jpa-rest)
This application is build with spring boot, JPA, MySql to expose RESTful API for a “notes” application.

## Requirements
1. Java - 1.8.x
2. Maven - 3.x.x
3. Mysql - 5.x.x

## Steps to Setup
**1. Clone the application**

```
git clone https://github.com/tabassumkherde/NotesApplication.git
```
**2. Create Mysql database**
```
create database notesdb
```
**3. Change mysql username and password as per your installation**
+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your mysql installation

```
mvn package
java -jar target/user-notes-1.0.0.jar

```
Alternatively, you can run the app without packaging it using -

```
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>.

## Basic HTTP Authentication 

The app use basic authentication, user need to enter below credential to access the api 

User name: root
Password:root


## Explore Rest APIs
The app defines following CRUD APIs.

```
    GET /api/notes/{userId}
    
    POST /api/notes/{userId}
    
    PUT /api/notes/{userId}/{noteId}
    
    DELETE /api/notes/{userId}/{noteId}

```

## Test Rest APIs

You can test them using swagger document or cURL commands.

Swagger documentation is available at <http://localhost:8080/swagger-ui.html>.

System inserted one default users into the database on start.

Once application is up user can perform below operation add new note using
 
```
Add Note:
url : http://localhost:8080/swagger-ui.html#!/notes45controller/addNoteUsingPOST

Get Note:
url : http://localhost:8080/swagger-ui.html#!/notes45controller/getAllNotesByUserUsingGET

Update Note:
url : http://localhost:8080/swagger-ui.html#!/notes45controller/updateNoteUsingPUT

Delete Note:
url : http://localhost:8080/swagger-ui.html#!/notes45controller/deleteNoteUsingDELETE

```

Swagger do provide the example value and try button to test api

