<h1 align="center">RESTful Web Services with Spring Boot</h1>

<h2>Spring Boot Architecture</h2>

| Layer                             | Component                                                                            | Purpose                                                                                                        |
| --------------------------------- | ------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------- |
| **1.** Presentation (`Web`)       | Controllers (@RestController), HTML pages (JSP), JSON or XML responses.              | Handles HTTP requests, processes input, and returns responses.                                                 |
| **2.** Business Logic (`Service`) | Services (@Service), business logic classes.                                         | Contains business logic and rules, processes data from the persistence layer and interacts with the web layer. |
| **3.** Persistence (`Repository`) | Repositories (@Repository), Data Access Objects (DAO), Spring Data JPA repositories. | Manages data access and persistence.                                                                           |
| **4.** Model (`Domain`)           | Entity classes (@Entity), POJOs (Plain Old Java Objects).                            | Represents the data structures or entities.                                                                    |

```mermaid
graph TD;
    A-->B;
    A-->C;
    B-->D;
    C-->D;
```

<h2>Spring Initializing</h2>

**1.** Go to the site: https://start.spring.io/

**2.** Select features.

| Feature     | Selection        |
| ----------- | ---------------- |
| Project     | `Maven`          |
| Langguage   | `Java`           |
| Spring Boot | `Latest Version` |
| Packaging   | `JAR`            |
| Java        | `17`             |

**3.** Set **Group** ID, **Artifact** ID, and Project Name.

**4.** Select dependencies.

| Dependency                     | Tag               |
| ------------------------------ | ----------------- |
| Spring Web                     | `WEB`             |
| Spring HATEOAS                 | `WEB`             |
| Rest Repositories HAL Explorer | `WEB`             |
| Spring Boot DevTools           | `DEVELOPER TOOLS` |
| Validation                     | `I/O`             |
| Spring Data JPA                | `SQL`             |
| H2 Database                    | `SQL`             |
| Spring Boot Actuator           | `OPS`             |
| Spring Security                | `SECURITY`        |

**5.** Click `Generate` (automatically download a zip file).

**6.** Unzip the `.ZIP` file.

**7.** Import that project folder from **Eclipse** (`Import` -> `Existing Maven Projects`).

**8.** Wait for **dependencies** download (`it really takes time for the first time using a specific version of Spring Boot or maybe my Internet capability sucks :D`).

<h2>Maven error "Failure to transfer..."</h2>

### Remove all failed downloads:

**1.** Run `cmd`.

**2.** `cd C:\Users\kiend\.m2\repository`.

**3.** `for /r %i in (*.lastUpdated) do del %i`.

**4.** Right click on the project in Eclipse -> `Maven` -> `Update Project...`

<h2 align="center">Configuration Notes</h2>

### Social Media Application

Local host: http://localhost:8080/

User -> Posts (one to many)

### User Endpoints

| Action             | Endpoints                      |
| ------------------ | ------------------------------ |
| Retrieve all Users | GET /users                     |
| Create a User      | POST /users                    |
| Retrieve one User  | GET /users/{id} -> /users/1    |
| Delete a User      | DELETE /users/{id} -> /users/1 |

### Posts Endpoints

| Action                        | Endpoints                       |
| ----------------------------- | ------------------------------- |
| Retrieve all posts for a User | GET /users/{id}/posts           |
| Create a posts for a User     | POST /users/{id}/posts          |
| Retrieve details of a post    | GET /users/{id}/posts/{post_id} |

<h2>Internationalization</h2>

| Configuration                                                                            | Usage                                                                                                                                                                 |
| ---------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| AcceptHeaderLocaleResolver<br>setDefaultLocale(Locale.US)<br>ResourceBundleMessageSource | @Autowired MessageSource<br>@RequestHeader(value = "Accept-Language", required = false)<br>Locale locale messageSource.getMessage("helloWorld.message", null, locale) |

<h2 align="center">Notes</h2>

- **Run server:** run file `{ProjectName}Application.java` as **Java Application**.

- Restart server after adding new dependencies: On **Console** bar, `Terminate` -> `Remove All Terminated Launches`, then `Run` again.

---

### RESPONSE STATUS

| Code  | Status             |
| :---: | ------------------ |
| `200` | SUCCESS            |
| `404` | RESOURCE NOT FOUND |
| `400` | BAD REQUEST        |
| `201` | CREATED            |
| `401` | UNAUTHORIZED       |
| `500` | SERVER ERROR       |

---

### Versioning

- Media type versioning (a.k.a `content negotiation` or `accept header`): **GitHub**
- (Custom) headers versioning: **Microsoft**
- URI Versioning: **Twitter**
- Parameter versioning: **Amazon**

| Factors                                    |
| ------------------------------------------ |
| URI Pollution                              |
| Misuse of HTTP Headers                     |
| Caching                                    |
| Can we execute the request on the browser? |
| API Documentation                          |

**--> No Perfect Solution**

---

- **Add dependency:** Remove the version tag -> Maven will use the version defined of the parent POM (`Spring framework` packages only).

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.1-SNAPSHOT</version>
    <relativePath /> <!-- lookup parent from repository -->
</parent>
```

- **XML** Content Supporting (Optional):

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

**Postman:** On **Header** section:

| Key    | Value           |
| ------ | --------------- |
| Accept | application/xml |

- Auto generation of Swagger documentation:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```
