# Spring Boot Rest API

Source code to run the Spring Boot Rest API.

## Project

Build a REST API to manage Bank Slips.

## Frameworks

This project uses the following frameworks in order to optimize the application source code and features:

* [Maven](https://maven.apache.org/)
* [Spring Boot](http://spring.io/)
* [H2 Database Engine](http://www.h2database.com/)

## Installation and Usage

```sh
git clone https://github.com/danielvito/dca-app.git
cd dca-app.git

# Install dependencies
mvn install

# Run Application
# It will start the server on localhost:8000/rest
# There are sample requets to import and run using Postman (see postman_samples.json)
# Open http://localhost:8000/rest/h2-console (user: root, pass: root) to use H2 console
mvn spring-boot:run

# Run Unit Tests (**/*Test.java classes)
mvn test

# Run Integrated Tests (**/*IT.java classes)
mvn verify
```

## License

All the code in this project is a public domain work, dedicated using [CC0 1.0](https://creativecommons.org/publicdomain/zero/1.0/). Feel free to do whatever you want with it.
