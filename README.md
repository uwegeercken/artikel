# Product Maintenance Application

Motivation for this code and application was to try to understand and implement the Hexagonal Architecture - also called Port and Adapter Architecture.
Side motivation was to try SparkJava (https://sparkjava.com/) - a micro framework for creating web applications.

The Web application allows for the maintenance of products, producers, markets, containers and orders. The application offers a shop feature where labels may be printed and orders may be placed for products put in the shop.

### Implementation details:
- the code in Hexagonal Architecture way.
- CSS and jQuery for frontend styling and functionality.
- Highcharts (https://www.highcharts.com) for charting price evolution.
- Sqlite database for persistence (https://sqlite.org).
- Web application using SparkJava and Apache Velocity template technology (https://velocity.apache.org/).
- Access to the application functionality usind Web Token and controlled by Open Policy Agent.

### Features:
- add, update, delete products, markets, producers, orders and more.
- flexible search of products. search products by last time changed.
- order products using a shop approach. history of orders.
- chart for selected products showing price changes over time.
- CSV loader to load data into the database.
- using glabels (Linux) to generate product labels.
- Asciidoc for order documents in PDF format. emailing order documents.
- serving documents relevant for the business.

## Web Application
### Preparations for the Web application:
- to generate the application jar file - dependencies are generated in the "lib" folder - image run:

    mvn clean package

- provide a yaml file with the configuration (see required attributes below). Samples for all configuration files are in the config folder.

If you want to deliver files through the Web UI - such as e.g. PDF files - in the configuration file specify the folder in the documentsFolder variable and copy the files to that location.

The application language is German (de) or English (en). But one could easily copy the resource file and translate it to a different language.

#### Configuration for the Web application:
The configuration file contains an entry for the database name. Adjust the path and name to your needs. The application needs access to the folder and write access for the database file. When no database file is found
at the defined location, the database will be created (if possible) and the database table structure will be setup. Make sure that the process has sufficient access rights to the folders defined in the configuration file.


The labels section specifies settings for output of product labels (for printing) using the glabels application. The glabels application needs to be installed. The tempFolder is used to output generated label pdf files.

The email section specifies parameters to access an email server to use when sending emails. An email template can be created which is used during generation of the email. The relevant order dokument in PDF format will be added as an attachment.

To produce a PDF file for an order, AsciiDoc is used. Asciidoc files are read from the variable "templateFileFolder" and have following naming convention:

    order_<producer id>.adoc

Although the filename ends with ".adoc", the file is first parsed using Apache Velocity and the products of the relevant order are inserted and then the PDF file is generated using Asciidoc. The example in the config folder can be used as a blueprint. 

Additionally an Asciidoc theme file is used, where the formatting of orders can be adjusted to individual needs. The filename of the theme is:

    asciidoc-theme-1.yml

You may adjust this file to your personal formatting needs of the order documents.

Product orders can be sent by email. The email body can be configured in the template: email_template_01.vm.

### Configuration file content:
Adjust the variables according to your needs, especially the folders where the different objects are stored and retrieved.

    database:
      jdbcClass: org.sqlite.JDBC
      connection: jdbc:sqlite
      name: /home/tester/products.db
    sparkJava:
      port: 4567
      staticfilesExpiretime: 60
      locale: de
      tempFolder: /tmp
      tokenExpiresMinutes: 180
      documentsFolder: /home/tester/documents
    opa:
        host: http://localhost:8181
    webApp:
        chartingNumberOfWeeksToDisplay: 10
        recentlyChangedProductsNumberOfDays: 2
        recentlyUnchangedProductsNumberOfDays: 30
        shorttermUnchangedProductsNumberOfDaysMin: 60
        shorttermUnchangedProductsNumberOfDaysMax: 90
        longtermUnchangedProductsNumberOfDaysMin: 90
    labels:
      glabelsBinary: /usr/bin/glabels-3-batch
      productLabelsFile: /home/tester/labels-01.glabels
      productStickersFile: /home/tester/stickers-01.glabels
      productStickersPrinterName: MyPrinter
      pdfOutputFolder: /home/tester/pdf
    asciidoc:
      templateFileFolder: /home/tester/asciidoc
      pdfOutputFolder: /home/tester/asciidoc/pdf
      themeFile: /home/tester/asciidoc/asciidoc-theme-1.yml
    email:
      templateFileFolder: /home/tester/email
      emailTemplateFilename: email_template_01.vm
      mailTransportProtocol: smtp
      mailSmtpHost: smtp.web.de
      mailSmtpPort: "587"
      mailSender: someuser@mymail.com
      mailSenderPassword: mypassword
      mailFrom: somebody@xyz.de

### User Roles
Users are categorized into three different roles:
- Read User: This user has read access and cannot modify any data
- Read and Write User: This user has read access and can execute create, update and delete operations. Additionally access to the shop, email and labels functionality is available.
- Admin User: This user has all privileges of the read/write user plus access to special functionality, such as user management.

Changes to user rights are immediately transmitted to the opa server.

Note: Access to the home page and the about page is available to anyone.

### Run the Open Policy Agent server
The application uses Open Policy Agent (https://www.openpolicyagent.org/) to secure the access to the different features.  
If you do not already have an Open Policy Agent server running, download it from https://www.openpolicyagent.org/docs/latest/#running-opa and run it:

    ./opa run --server

Make sure that you manually create a backup of the database at regular intervals.

Note: The Open Policy Agent server is configured from the application on startup - you do not need to provide anything to run the server.

### Run the Web application
Make sure the Open Policy Agent server is running. Then, provide the path and name of the configuration file - please adjust it to your individual needs - and run:

    java -jar artikel.jar config.yaml

Note: On first run, the database and an administrative user will be created in the database. The administrator name and password is output to the log. Make sure you change the password after you logged in. 

### Run the application in a container
To generate an OCI compliant container image run:

    mvn clean install

This will use buildah to generate the container image, so make sure buildah is installed. The container comes without any configuration - you
will have to volume mount a folder containing all required files (to the folder opt/artikel/config in the container) when you run the container.

Adjust the config.yaml file according to your settings before you run the container.

Note: to run the application you need to have an OPA (Open Policy Agent) server running. Specify the OPA address and port in the config file.

Run the container like e.g. this:

    podman run --name "artikel-test" --rm -it -p 5000:4567 -v ./config:/opt/artikel/config:z artikel:latest

## CSV Loader
### Loading CSV Data from files
Load basic data from CSV files into the database. There are sample files available in this repository.

### Configuration file content:
The configuration yaml file needs to contain - if you want to load data from csv files - the path where the csv files are
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
        usersFilename: users.csv

### Run the CSV Loader application
provide the path and name of the configuration file and run:

    java -cp artikel.jar com.datamelt.artikel.app.csv.CsvLoaderApplication config.yaml


Copyright Uwe Geercken, 2022-2025. Last update: 2025-06-03
