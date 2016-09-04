# DHS_FLASH

#How to use flashservices project?

1) Install postgres BigSQL Manager: http://localhost:8050/#/details-pg/pg95

2) Create dhsflash database

createdb -h localhost -p 5432 -U postgres dhsflash

3) Run https://github.com/inalabaws/DHS_FLASH/blob/master/flashservices/src/main/resources/db/db.sql to create tables

4) checkout flashservices project from develop branch [ not from master ]

5) Go to eclipse and import flashservices project. 

Note: If any issue with eclipse import then run "gradle eclipse" to regrenerate eclipse classpath and project files

6) Run 

- "gradle clean build run" to test project in local
- "gradle clean build war" to generate war file

7) Once project is running in local then go to http://localhost:8080/swagger-ui.html to test services from UI e.g click on login-controller and enter values to create or retrieve user. JSON response will be displayed in the result window.
