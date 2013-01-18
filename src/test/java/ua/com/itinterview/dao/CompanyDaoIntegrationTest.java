package ua.com.itinterview.dao;

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

    @Override
    protected EntityWithIdDao<CompanyEntity> getEntityWithIdDao() {
	return companyDao;
    }

}
