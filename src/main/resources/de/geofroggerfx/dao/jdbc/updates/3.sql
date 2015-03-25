CREATE TABLE log (
  id        INT PRIMARY KEY ,
  cache_id  INT,
  date      DATETIME,
  user_id   INT,
  type      VARCHAR(100),
  text      VARCHAR(2147483647),
  FOREIGN KEY(cache_id) REFERENCES cache(id) ON DELETE CASCADE
);

UPDATE version SET version=3;