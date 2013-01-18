create table company (
   id integer not null,
   companyName varchar(255),
   companyPhone varchar (255),
   companyLogoUrl varchar(255),
   companyAddress varchar(255),
   companyWebPage varchar(255),
   companyType varchar(255),
   primary key (id)
);
create sequence company_id start with 1;