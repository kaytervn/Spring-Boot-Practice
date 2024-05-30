<h1 align="center">RESTful Web Services with Spring Boot</h1>

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

| Dependency           | Tag               |
| -------------------- | ----------------- |
| Spring Web           | `WEB`             |
| Spring HATEOAS       | `WEB`             |
| Spring Boot DevTools | `DEVELOPER TOOLS` |
| Validation           | `I/O`             |
| Spring Data JPA      | `SQL`             |
| H2 Database          | `SQL`             |

**5.** Click `Generate` (automatically download a zip file).

**6.** Unzip the `.ZIP` file.

**7.** Import that project folder from **Eclipse** (`Import` -> `Existing Maven Projects`).

**8.** Wait for **dependencies** download `(it really takes time for the first time using a specific version of Spring Boot or maybe my Internet capability sucks :D)`.

<h2>Maven error "Failure to transfer..."</h2>

Remove all failed downloads:

**1.** Run `cmd`.

**2.** `cd C:\Users\kiend\.m2\repository`.

**3.** `for /r %i in (*.lastUpdated) do del %i`.

**4.** Right click on the project in Eclipse -> `Maven` -> `Update Project...`

<h2 align="center">RESTful Web Services</h2>

**Social Media Application**

Local host: http://localhost:8080/

User -> Posts (one to many)

| Users Action       | Endpoints                      |
| ------------------ | ------------------------------ |
| Retrieve all Users | GET /users                     |
| Create a User      | POST /users                    |
| Retrieve one User  | GET /users/{id} -> /users/1    |
| Delete a User      | DELETE /users/{id} -> /users/1 |

---

| Posts Action                  | Endpoints                       |
| ----------------------------- | ------------------------------- |
| Retrieve all posts for a User | GET /users/{id}/posts           |
| Create a posts for a User     | POST /users/{id}/posts          |
| Retrieve details of a post    | GET /users/{id}/posts/{post_id} |

merge 1