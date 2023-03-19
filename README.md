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
```
You might have to right click pom.xml -> maven -> reload project if ur getting build errors

Alternatively you can just run the ServiceManualApplication.java in IntelliJ

### 3. Run Tests

```
$ cd Huoltokirja-harjoitus
$ mvn test
```
Alternatively you can just run the tests by right clicking the servicemanual folder and choosing "Run tests" in IntelliJ

The folder is located at \Huoltokirja-harjoitus\src\test\java\com\etteplan\servicemanual

### 4. Swagger Docs

```
$ Once you have the app running you can access the api docs at http://localhost:9000/swagger-ui.html
```
You can see a summary of what every endpoint does and what inputs it takes in

![image](https://user-images.githubusercontent.com/60407896/226191367-f123c947-10b1-453d-9d63-8732aa06b41d.png)
