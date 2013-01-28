CREATE TABLE feedbacks (
  id           INTEGER NOT NULL,
  interviewId INTEGER NOT NULL,
  createTime   TIMESTAMP,
  checked      BOOLEAN,
  feedbackText TEXT,
  PRIMARY KEY (id)
);

CREATE SEQUENCE feedbacks_id START WITH 1;

ALTER TABLE feedbacks
ADD CONSTRAINT feedbacks_interview_fk 
FOREIGN KEY (interviewId)
REFERENCES interview (id);
