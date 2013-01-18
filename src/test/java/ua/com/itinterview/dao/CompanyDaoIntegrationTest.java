package ua.com.itinterview.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.CompanyEntity;
import ua.com.itinterview.entity.CompanyEntity.CompanyType;

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
	companyEntity2.setType(CompanyType.gt200lt800);
	companyDao.save(companyEntity2);
	
	CompanyEntity companyEntity3 = new CompanyEntity();
	companyEntity3.setCompanyName("BCompany");
	companyEntity3.setType(CompanyType.lt80);
	companyDao.save(companyEntity3);
    }
    
    @Test
    public void testGetAllCompanies() {
	List<CompanyEntity> companies = companyDao.getCompanies();
	assertEquals(4, companies.size());
    }
    
    @Test
    public void testGetAllCompaniesOrdered() {
	List<CompanyEntity> companies = companyDao.getAllCompaniesOrdered("companyName");
	assertEquals("ACompany", companies.get(0).getCompanyName());
    }

    @Override
    protected EntityWithIdDao<CompanyEntity> getEntityWithIdDao() {
	return companyDao;
    }

}
