# Artikelverwaltung

Motivation for this code and application was to try to understand and implement the Hexagonal Architecture - also called Port and Adapter Architecture.
Side motivation was to try SparkJava (https://sparkjava.com/) - a micro framework for creating web applications.

The implementation is:
- the code in Hexagonal Architecture way
- CSS and jQuery for frontend styling and functionality
- Sqlite Database for persistence (https://sqlite.org)
- a Web application using SparkJava and Apache Velocity technology (https://velocity.apache.org/)
- a CSV loader to load data into the database

###Preparations for the Web application:
- run mvn clean install to generate the application jar file. dependencies will be in the generated .lib folder
- provide a yaml file with the configuration

The configuration file contains an entry for the database name. Adjust the path and name to your needs. The application needs access to the folder and write access for the database file. When no database file is found
at the defined location, the database will be created (if possible) and the database table structure will be setup.

###Sample configuration file content:

    database:
      jdbcClass: org.sqlite.JDBC
      connection: jdbc:sqlite
      name: /home/tester/products.db


###Run the Web application
provide the path and name of the configuration file and run:

    java -jar artikel.jar config.yaml


Copyright Uwe Geercken, 2022. Last update: 2022-04-15
