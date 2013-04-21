CREATE TABLE users (
  id       INTEGER NOT NULL,
  userName VARCHAR(255),
  name VARCHAR(255),
  password VARCHAR(64),
  email    VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE SEQUENCE users_id START WITH 1;

CREATE TABLE comments (
  id          INTEGER      NOT NULL,
  question_id INTEGER      NOT NULL,
  rate        INTEGER      NOT NULL,
  authorName  VARCHAR(255) NOT NULL,
  email       VARCHAR(255),
  commentText TEXT         NOT NULL,
  userpicUrl  VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE SEQUENCE comments_id START WITH 1;

CREATE TABLE interview (
  id       INTEGER NOT NULL,
  userId   INTEGER NOT NULL,
  feedback VARCHAR(255),
  created  TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE SEQUENCE interview_id START WITH 1;
