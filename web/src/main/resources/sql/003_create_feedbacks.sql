CREATE TABLE feedbacks (
  id           INTEGER NOT NULL,
  createTime   TIMESTAMP,
  checked      BOOLEAN,
  feedbackText TEXT,
  PRIMARY KEY (id)
);

CREATE SEQUENCE feedbacks_id START WITH 1;
