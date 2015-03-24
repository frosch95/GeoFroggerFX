CREATE TABLE attribute (
  id        INT,
  cache_id  INT,
  PRIMARY KEY (id, cache_id),
  FOREIGN KEY(cache_id) REFERENCES cache(id) ON DELETE CASCADE
);

INSERT INTO version(version) values(2);