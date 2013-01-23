ALTER TABLE comments ADD CONSTRAINT comments_question_fk FOREIGN KEY (question_id) REFERENCES interview_question (id);
