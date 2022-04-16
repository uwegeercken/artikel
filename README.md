# Product Maintenance Application

Motivation for this code and application was to try to understand and implement the Hexagonal Architecture - also called Port and Adapter Architecture.
Side motivation was to try SparkJava (https://sparkjava.com/) - a micro framework for creating web applications.

The Web application allows for the maintenance of products, producers, markets, containers and orders.

### Implementation details:
- the code in Hexagonal Architecture way
- CSS and jQuery for frontend styling and functionality
- Sqlite Database for persistence (https://sqlite.org)
- a Web application using SparkJava and Apache Velocity technology (https://velocity.apache.org/)
- a CSV loader to load data into the database

## Web Application
### Preparations for the Web application:
- run mvn clean install to generate the application jar file. dependencies will be in the generated .lib folder
- provide a yaml file with the configuration

The configuration file contains an entry for the database name. Adjust the path and name to your needs. The application needs access to the folder and write access for the database file. When no database file is found
at the defined location, the database will be created (if possible) and the database table structure will be setup.

### Configuration file content:

    database:
      jdbcClass: org.sqlite.JDBC
      connection: jdbc:sqlite
      name: /home/tester/products.db
    sparkJava:
      staticfilesExpiretime: 60

### Run the Web application
provide the path and name of the configuration file and run:

    java -jar artikel.jar config.yaml

## CSV Loader
### Loading CSV Data from files
Load basic data from CSV files into the database. The file are contained in the application jar file.

### Configuration file content:
The configuration yaml file needs to contain - additionally to the configuration from above - the path where the csv files are
located and a list of filenames for the different files:

    csvInput:
        filesFolder: /home/uwe/development/csv
        productsFilename: products.csv
        producersFilename: producers.csv
        marketsFilename: markets.csv
        ordersFilename: orders.csv
        orderitemsFilename: orderitems.csv
        productContainersFilename: containers.csv
        productOriginsFilename: origins.csv

### Run the CSV Loader application
provide the path and name of the configuration file and run:

    java -cp artikel.jar com.datamelt.artikel.app.csv.CsvLoaderApplication config.yaml


Copyright Uwe Geercken, 2022. Last update: 2022-04-16
