CREATE TABLE interview_question (
  id INTEGER NOT NULL,
  interviewId INTEGER NOT NULL,
  question TEXT NOT NULL,
  PRIMARY KEY (id)
);

CREATE SEQUENCE interview_question_id START WITH 1;