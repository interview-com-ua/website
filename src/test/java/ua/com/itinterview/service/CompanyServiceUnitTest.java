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

    private static final String companyName = "company";
    private static final String companyPhone = "company phone";
    private static final String companyLogoUrl = "company logo url";
    private static final String companyAddress = "company address";
    private static final String companyWebPage = "company webpage";

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
	    company.setCompanyName(companyName + i);
	    company.setCompanyPhone(companyPhone + i);
	    company.setCompanyLogoUrl(companyLogoUrl + i);
	    company.setCompanyAddress(companyAddress + i);
	    company.setCompanyWebPage(companyWebPage + i);

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
	company.setName(companyName);
	company.setPhone(companyPhone);
	company.setLogoURL(companyLogoUrl);
	company.setAddress(companyAddress);
	company.setWebPage(companyWebPage);

	return company;
    }

    private CompanyEntity createTestCompanyEntity() {

	CompanyEntity company = new CompanyEntity();
	company.setCompanyName(companyName);
	company.setCompanyPhone(companyPhone);
	company.setCompanyLogoUrl(companyLogoUrl);
	company.setCompanyAddress(companyAddress);
	company.setCompanyWebPage(companyWebPage);

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
