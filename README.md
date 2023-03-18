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
