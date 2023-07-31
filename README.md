#Indiverse
Spring backend for indieverse, an indie game publishing website created.
## Tech stack
* Spring Boot MVC
* Spring Security
* Spring Validation
* Spring Data JPA
* JWT
## Running
    A docker compose is provided, simply run in the project root
```
        docker-compose -f docker-compose.yaml up
```
Alternatevely to run the application there should be mariadb
running on localhost with user with name 'myuser' and password 'password'
these values can be changed in application.properties.
