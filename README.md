# graphql-jdbc
A sample Spring boot application for GraphQL service data through JDBC and XOR

It allows an end user to interact directly with an RDBMS database using XOR and GraphQL.
No code is necessary apart from initializing the database connection details.

For this sample application we use the northwind schema from the following repo:
https://github.com/pthom/northwind_psql

The script included in this repo is for the PostgreSQL database. So you will need to have a schema populated with this script to follow the examples described below.

startup the server by running:
mvn spring-boot:run

or with debugging
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

Get the GraphQL schema for the Northwind DB by running:
curl http://localhost:8080/graphql
