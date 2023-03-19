###

### 1. Clone repo

```
$ git clone https://github.com/Compeee/Huoltokirja-harjoitus.git
```

### 2. Setup Database

```
$ cd Huoltokirja-harjoitus
$ docker-compose up -d
```

### 3. Run Backend

```
$ cd Huoltokirja-harjoitus/src/main/java/com/etteplan/servicemanual
$ mvn spring-boot:run
$ You might have to right click pom.xml -> maven -> reload project if ur getting build errors
$ Alternatively you can just run the ServiceManualApplication.java in IntelliJ
```

### 3. Run Tests

```
$ cd Huoltokirja-harjoitus
$ mvn test
$ Alternatively you can just run the tests by right clicking the servicemanual folder and choosing "Run tests" in IntelliJ
$ The folder is located here Huoltokirja-harjoitus\src\test\java\com\etteplan\servicemanual
```
### 4. Swagger Docs

```
$ http://localhost:9000/swagger-ui/index.html
```
