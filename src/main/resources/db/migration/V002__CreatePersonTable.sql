 CREATE TABLE person (
     id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
     name VARCHAR NOT NULL,
     vorname VARCHAR NOT NULL,
     geburtsdatum DATE(10) NOT NULL
 )