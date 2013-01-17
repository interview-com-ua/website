create table users (id integer not null, userName varchar(255), password varchar (255), email varchar(255), primary key (id));
create sequence users_id start with 1;

create table comments (
    id integer not null,
    question_id not null,
    rate integer not null,
    authorName varchar(255) not null,
    email varchar(255),
    commentText text not null,
    userpicUrl varchar(255),
    primary key (id),
    foreign key question_id references questions(id)
);
create sequence comments_id start with 1;

CREATE TABLE interview (userId INTEGER NOT NULL, feedback VARCHAR(255), created TIMESTAMP, FOREIGN KEY userId REFERENCES users(id));

CREATE SEQUENCE interview_id START WITH 1;
