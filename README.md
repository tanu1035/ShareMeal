# ShareMeal - Smart Food Donation and Redistribution System

Console based Spring Boot application (Java 17, Spring Data JPA, MySQL).

## Prerequisites
- JDK 17+
- Maven 3.8+
- MySQL 8 running on localhost:3306

## Run
```
set DB_USER=root          (Linux/Mac: export DB_USER=root)
set DB_PASSWORD=yourpass  (Linux/Mac: export DB_PASSWORD=yourpass)
mvn spring-boot:run
```
The database `sharemeal` and its tables are created automatically on first run.
`sql/schema.sql` contains the same design for your report / submission.

Demo admin credentials, if configured, should be obtained from the project setup instructions. Change any default credentials before deployment.
## Tests
```
mvn test
```

## Module status
- [x] Module 1: setup, entities, registration, login, role based menus
- [ ] Module 2: donations (add, list, cancel)
- [ ] Module 3: requests (request, approve/reject with @Transactional)
- [ ] Module 4: pickup confirmation + @Scheduled expiry job
- [ ] Module 5: reports (Streams) + CSV export
