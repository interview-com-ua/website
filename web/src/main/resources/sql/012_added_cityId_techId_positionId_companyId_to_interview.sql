alter table interview add column city_id integer;
alter table interview add constraint interview_city_id foreign key (city_id) references city;

alter table interview add column position_id integer;
alter table interview add constraint interview_position_id foreign key (position_id) references positions;

alter table interview add column technology_id integer;
alter table interview add constraint interview_technology_id foreign key (technology_id) references technologies;

alter table interview add column company_id integer;
alter table interview add constraint interview_company_id foreign key (company_id) references company;