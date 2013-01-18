CREATE TABLE users (
    id INTEGER NOT NULL,
    userName VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE SEQUENCE users_id START with 1;

CREATE TABLE comments (
    id INTEGER NOT NULL,
    question_id INTEGER NOT NULL,
    rate INTEGER NOT NULL,
    authorName VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    commentText text NOT NULL,
    userpicUrl VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE SEQUENCE comments_id START WITH 1;

CREATE TABLE interview (
    userId INTEGER NOT NULL,
    feedback VARCHAR(255),
    created TIMESTAMP
);

CREATE SEQUENCE interview_id START WITH 1;
