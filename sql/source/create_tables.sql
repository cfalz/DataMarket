
DROP TABLE Users CASCADE;
DROP TABLE Data   CASCADE;
DROP TABLE Source CASCADE;
DROP TABLE Device CASCADE;
DROP TABLE Source CASCADE;


CREATE TABLE Users
(
  name                 char(50),
	login                char(50)        UNIQUE NOT NULL,
	phoneNumber          char(16)        UNIQUE,
	password             char(50)        NOT NULL,
  email                char(50)        UNIQUE,
	type                 char(8)         NOT NULL,
	PRIMARY KEY(login);
);

CREATE TABLE Privileges
(
	   login           char(50)   NOT NULL,
     dataId          serial      UNIQUE     NOT NULL,
     privilegeId          serial      UNIQUE     NOT NULL,
     FOREIGN KEY(login) REFERENCES Users(login),
     FOREIGN KEY(dataId) REFERENCES Data(dataId),
     PRIMARY KEY (login, id);
)

CREATE TABLE Data
(
    name        char(50),
    dataId          serial      UNIQUE     NOT NULL,
    sourceId    serial      UNIQUE     NOT NULL,
    PRIMARY KEY (dataId);
)

CREATE TABLE Source
(
    name              char(50)    NOT NULL,
    sourceId          serial      UNIQUE    NOT NULL,
    PRIMARY KEY (sourceId);
)

CREATE TABLE File
(
    name      char(50)    NOT NULL,
    fileId        serial      UNIQUE      NOT NULL
    sourceId          serial      UNIQUE    NOT NULL,
    FOREIGN KEY(sourceId) REFERENCES Source(sourceId),
    PRIMARY KEY (fileId);
)

CREATE TABLE Device
(
    name      char(50)    NOT NULL,
    deviceId        serial      UNIQUE      NOT NULL
    sourceId          serial      UNIQUE    NOT NULL,
    FOREIGN KEY(sourceId) REFERENCES Source(sourceId),
    PRIMARY KEY (deviceId);

)
