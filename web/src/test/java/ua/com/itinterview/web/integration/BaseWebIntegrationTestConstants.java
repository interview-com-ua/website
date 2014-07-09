package ua.com.itinterview.web.integration;

import ua.com.itinterview.entity.CompanyEntity;

/**
 * User: Vladimir
 * Date: 09.11.13
 */
public interface BaseWebIntegrationTestConstants {
    String NAME = "Name";
    String NAME_ANOTHER = "Name2";
    String EMAIL = "email@mail.com";
    String EMAIL_ANOTHER = "email2@mail.com";
    String USER_ID = "10";
    String PASSWORD = "password";
    String PASSWORD_ANOTHER = "password2";
    String INVALID_EMAIL = "invalid_email";
    String USER_FEEDBACK = "Feedback here";

    String COMPANY_NAME = "Company name here";
    String COMPANY_ADDRESS = "Company address here";
    String COMPANY_LOGO = "http://logo.com";
    String COMPANY_PHONE_NUMBER = "123-456-789";
    String COMPANY_WEB_PAGE = "http://company.com";
    CompanyEntity.CompanyType COMPANY_TYPE = CompanyEntity.CompanyType.lt80;

    String CITY_NAME = "City name here";

    String TECHNOLOGY_NAME = "Java";

    String POSITION_NAME = "Java Junior Developer";
    String POSITION_GROUP = "Position group here";
}
