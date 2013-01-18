package ua.com.itinterview.dao;

import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.CompanyEntity;

public class CompanyDao extends EntityWithIdDao<CompanyEntity> {
    
    @Transactional
    public CompanyEntity getCompanyByCompanyName(String companyName) {
	return null;
    }
}
