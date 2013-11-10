package ua.com.itinterview.web.integration;

import ua.com.itinterview.entity.CompanyEntity;
import ua.com.itinterview.entity.UserEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Vladimir
 * Date: 09.11.13
 * Time: 19:48
 * To change this template use File | Settings | File Templates.
 */
public interface BaseWebIntegrationTestConstants {
    String USER_NAME = "UserName";
    String USER_NAME_ANOTHER = "UserName2";
    String NAME = "Name";
    String NAME_ANOTHER = "Name2";
    String EMAIL = "email@mail.com";
    String EMAIL_ANOTHER = "email2@mail.com";
    String PASSWORD = "password";
    String PASSWORD_ANOTHER = "password2";
    UserEntity.Sex SEX = UserEntity.Sex.FEMALE;
    UserEntity.Sex SEX_ANOTHER = UserEntity.Sex.MALE;
    String INVALID_USER_NAME = "$%#^";
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
