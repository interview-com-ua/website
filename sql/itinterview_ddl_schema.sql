create table users (id integer not null, userName varchar(255), password varchar (255), email varchar(255), primary key (id));
create sequence users_id start with 1;
create table positions (id integer not null, positionGroup varchar(255), positionName varchar (255), primary key (id));
create sequence positions_id start with 1;
create table technologies (id integer not null, technologyName varchar(255), primary key (id));
create sequence technologies_id start with 1;