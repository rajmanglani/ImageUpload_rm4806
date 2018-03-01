# ImageUpload_rm4806
The application is coded in Java. Java Server Faces (JSF) framework is used with Primefaces for UI. To get started, install Netbeans IDE (8.2) with Java EE components. Once done, follow these steps (be sure to install correct versions):
1. Install glassfish server (4.1).
2. Netbeans comes with Java DB which is used as the database for this project. 

TO CREATE DATABASE:
1. Go to Services tab on Netbeans IDE.
2. Click on Databases, right click on JavaDB and create database. 
3. Name it ImageUpload (case sensitive).
4. Set username as 'app' and passwords as '12345' (all case sensitive)
5. Execute the following SQL commands:
    CREATE TABLE ACCOUNTS(
    EMAIL VARCHAR(256) NOT NULL PRIMARY KEY,
    PASSWORD VARCHAR(256),
    FNAME VARCHAR(256),
    LNAME VARCHAR(256)
    );
    CREATE TABLE IMAGES(
    IMG_ID TIMESTAMP NOT NULL PRIMARY KEY,
    IMG BLOB,
    CAPTION VARCHAR(1024),
    EMAIL VARCHAR(256),
    FOREIGN KEY(EMAIL) REFERENCES ACCOUNTS(EMAIL)
    );
6. Once the DB is setup, it is time to set up the connection pool on glassfish server. 

TO CREATE CONNECTION POOL:
1. Go to Services tab on Netbans
2. Right click on GlassFish server and 'View Domain Admin Console'
3. The admin page will open up on the browser. 
4. Click on JDBC on left nav and then JDBC Connection pools. 
5. Click on New, enter pool name as 'ImageUp'. 
6. Select Resource type as javax.sql.DataSource
7. Select DB driver vendor as Java DB and click next
8. Under Additional properties table, enter these six properties and delete the rest
  User - app
  DatabaseName - ImageUpload
  URL - jdbc:derby://localhost:1527/ImageUpload
  ServerName - localhost
  PortNumber - 1527
  Password - 12345
9. Save the pool and now click on JDBC Resources under JDBC. 
10. Click on New and give JNDI name as jdbc/imgUpload
11. Select the connection pool from dropdown. Save the resource. 

RUN THE CODE:
1. Download the zip file from git and extract it under the Netbeans Projects directory create while installing. 
2. The code needs apache commons-io-2.6 jar which can be downloaded from https://commons.apache.org/proper/commons-io/download_io.cgi
3. Open the project and run the war file (web app java).
4. If there is error running, make sure the EJB is built and deployed. Add its jar file to the libraries folder under war application. 

Contact rm4806@nyu.edu for information regarding any erros while setting up the web app. 
