package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.CompanyDao;
import ua.com.itinterview.entity.CompanyEntity;
import ua.com.itinterview.web.command.CompanyCommand;

public class CompanyServiceUnitTest {

    private static final Integer ID = 14;
    private static final String COMPANY_NAME = "company";
    private static final String COMPANY_PHONE = "company phone";
    private static final String COMPANY_LOGO_URL = "company logo url";
    private static final String COMPANY_ADDRESS = "company address";
    private static final String COMPANY_WEB_PAGE = "company webpage";

    private CompanyService companyService;
    private CompanyDao companyDao;

    @Before
    public void setupMocks() {

	companyDao = EasyMock.createMock(CompanyDao.class);
	companyService = new CompanyService();
	companyService.companyDao = companyDao;
    }

    private void replayMocks() {
	EasyMock.replay(companyDao);
    }

    @After
    public void verifyMocks() {
	EasyMock.verify(companyDao);
    }

    private List<CompanyEntity> generateCompanyList() {

	List<CompanyEntity> companies = new ArrayList<CompanyEntity>();

	for (int i = 1; i <= 3; i++) {

	    CompanyEntity company = new CompanyEntity();
	    company.setCompanyName(COMPANY_NAME + i);
	    company.setCompanyPhone(COMPANY_PHONE + i);
	    company.setCompanyLogoUrl(COMPANY_LOGO_URL + i);
	    company.setCompanyAddress(COMPANY_ADDRESS + i);
	    company.setCompanyWebPage(COMPANY_WEB_PAGE + i);

	    companies.add(company);
	}

	return companies;
    }

    private List<CompanyCommand> convertToCommandList(
	    List<CompanyEntity> entities) {

	List<CompanyCommand> companies = new ArrayList<CompanyCommand>();

	for (CompanyEntity entity : entities) {
	    companies.add(new CompanyCommand(entity));
	}

	return companies;
    }

    private CompanyCommand createTestCompanyCommand() {

	CompanyCommand company = new CompanyCommand();
	company.setName(COMPANY_NAME);
	company.setPhone(COMPANY_PHONE);
	company.setLogoURL(COMPANY_LOGO_URL);
	company.setAddress(COMPANY_ADDRESS);
	company.setWebPage(COMPANY_WEB_PAGE);
	company.setId(ID);
	
	return company;
    }

    private CompanyEntity createTestCompanyEntity() {

	CompanyEntity company = new CompanyEntity();
	company.setCompanyName(COMPANY_NAME);
	company.setCompanyPhone(COMPANY_PHONE);
	company.setCompanyLogoUrl(COMPANY_LOGO_URL);
	company.setCompanyAddress(COMPANY_ADDRESS);
	company.setCompanyWebPage(COMPANY_WEB_PAGE);
	company.setId(ID);
	
	return company;
    }

    @Test
    public void testConvertEntityToCommand() {

	replayMocks();
	CompanyCommand expected = createTestCompanyCommand();
	assertEquals(expected, new CompanyCommand(createTestCompanyEntity()));
    }

    @Test
    public void testGetCompanyList() {

	List<CompanyEntity> companies = generateCompanyList();

	EasyMock.expect(companyDao.getAllOrderedBy("companyName", 0))
		.andReturn(companies);
	replayMocks();
	assertEquals(convertToCommandList(companies),
		companyService.getCompanyList());
    }

}
