CREATE TABLE company (
  id             INTEGER NOT NULL,
  companyName    VARCHAR(255),
  companyPhone   VARCHAR(255),
  companyLogoUrl VARCHAR(255),
  companyAddress VARCHAR(255),
  companyWebPage VARCHAR(255),
  companyType    VARCHAR(255),
  PRIMARY KEY (id)
);
CREATE SEQUENCE company_id START WITH 1;