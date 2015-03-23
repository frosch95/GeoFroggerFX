CREATE TABLE cache (
  id                      INT PRIMARY KEY,
  name                    VARCHAR (255),
  placed_by               VARCHAR (255),
  available               BOOLEAN,
  archived                BOOLEAN,
  found                   BOOLEAN,
  difficulty              VARCHAR (255),
  terrain                 VARCHAR (255),
  country                 VARCHAR (255),
  state                   VARCHAR (255),
  short_description       VARCHAR(2147483647),
  short_description_html  BOOLEAN,
  long_description        VARCHAR(2147483647),
  long_description_html   BOOLEAN,
  encoded_hints           VARCHAR(2147483647),
  container               VARCHAR (255),
  type                    VARCHAR (255),
  user_id                 INT
);

CREATE TABLE waypoint (
  name      VARCHAR (255) PRIMARY KEY ,
  cache_id  INT,
  latitude  DOUBLE,
  longitude DOUBLE,
  time      TIMESTAMP,
  description VARCHAR (255),
  url     VARCHAR (255),
  urlName VARCHAR (255),
  symbol  VARCHAR (255),
  type    VARCHAR (255),
  FOREIGN KEY(cache_id) REFERENCES cache(id) ON DELETE CASCADE
);

CREATE TABLE version (
  version INT
);

INSERT INTO version(version) values(1);