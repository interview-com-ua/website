package ua.com.itinterview.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.entity.CompanyEntity;
import ua.com.itinterview.entity.CompanyEntity.CompanyType;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CompanyDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<CompanyEntity> {

    @Autowired
    CompanyDao companyDao;
    
    @Override
    protected CompanyEntity createEntity() {
	CompanyEntity companyEntity = new CompanyEntity();
	companyEntity.setId(666);
	companyEntity.setCompanyName("Microsoft");
	companyEntity.setCompanyPhone("(800) 642-7676");
	companyEntity.setType(CompanyType.gt800);
	companyEntity.setCompanyAddress("microsoft.com");

	return companyEntity;
    }

    @Before
    public void init() {
	CompanyEntity companyEntity1 = new CompanyEntity();
	companyEntity1.setCompanyName("CCompany");
	companyEntity1.setType(CompanyType.gt80lt200);
	companyDao.save(companyEntity1);
	
	CompanyEntity companyEntity2 = new CompanyEntity();
	companyEntity2.setCompanyName("ACompany");
	companyEntity2.setType(CompanyType.gt80lt200);
	companyDao.save(companyEntity2);
	
	CompanyEntity companyEntity3 = new CompanyEntity();
	companyEntity3.setCompanyName("BCompany");
	companyEntity3.setType(CompanyType.lt80);
	companyDao.save(companyEntity3);
    }
    
    @Test
    public void testGetCompaniesByName() {
	CompanyEntity company = companyDao.getCompanyByCompanyName("BCompany");
	assertEquals(company.getCompanyName(), "BCompany");
    }
    
    @Test(expected = EntityNotFoundException.class)
    public void testGetUserByEmailWhenNotExistsAndRecesiveException() {
	companyDao.getCompanyByCompanyName("SomeWrongCompanyName!!!");
    }
    
    @Test
    public void testGetCompanies() {
	List<CompanyEntity> companies = companyDao.getCompanies();
	assertEquals(3, companies.size());
    }
    
    @Test
    public void testGetCompaniesLimit() {
	List<CompanyEntity> companies = companyDao.getCompanies(2);
	assertEquals(2, companies.size());
    }
    
    @Test
    public void testGetCompaniesOrdered() {
	List<CompanyEntity> companies = companyDao.getCompaniesOrdered("companyName");
	assertEquals("ACompany", companies.get(0).getCompanyName());
    }
    
    @Test
    public void testGetCompaniesOrderedLimit() {
	List<CompanyEntity> companies = companyDao.getCompaniesOrdered("companyName", 2);
	assertEquals("ACompany", companies.get(0).getCompanyName());
	assertEquals(2, companies.size());
    }
    
    @Test
    public void testGetCompaniesByType() {
	List<CompanyEntity> companies1 = companyDao.getCompaniesByType(CompanyType.gt80lt200);
	assertEquals(2, companies1.size());
	
	List<CompanyEntity> companies2 = companyDao.getCompaniesByType(CompanyType.lt80);
	assertEquals(1, companies2.size());
    }
    
    @Test
    public void testGetCompaniesByTypeLimit() {
	List<CompanyEntity> companies1 = companyDao.getCompaniesByType(CompanyType.gt80lt200, 1);
	assertEquals(1, companies1.size());
	
	List<CompanyEntity> companies2 = companyDao.getCompaniesByType(CompanyType.lt80);
	assertEquals(1, companies2.size());
    }

    @Override
    protected EntityWithIdDao<CompanyEntity> getEntityWithIdDao() {
	return companyDao;
    }

}
