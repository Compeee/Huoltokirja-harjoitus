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
$ cd Huoltokirja-harjoitus
$ mvn spring-boot:run
```
The database is seeded on run with the data from seeddata.csv along with a few service tasks.

You might have to open the project in IntelliJ and right click pom.xml -> maven -> reload project if ur getting build errors

Alternatively you can just run the ServiceManualApplication.java in IntelliJ 
which is located at Huoltokirja-harjoitus\src\main\java\com\etteplan\servicemanual\ServiceManualApplication.java

### 4. Run Tests

```
$ cd Huoltokirja-harjoitus
$ mvn test
```
Alternatively you can just run the tests by right clicking the servicemanual folder and choosing "Run tests" in IntelliJ

The folder is located at \Huoltokirja-harjoitus\src\test\java\com\etteplan\servicemanual

### 5. Swagger Docs

```
$ Once you have the app running you can access the api docs at http://localhost:9000/swagger-ui.html
```
You can see a summary of what every endpoint does and what inputs it takes in

![image](https://user-images.githubusercontent.com/60407896/226338038-f59be3ec-ba60-4131-9195-7c48bdabbd1b.png)
