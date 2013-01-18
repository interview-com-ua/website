package ua.com.itinterview.dao;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.entity.CompanyEntity;

public class CompanyDao extends EntityWithIdDao<CompanyEntity> {
    
    @Transactional
    public CompanyEntity getCompanyByCompanyName(String companyName) {
	return null;
    }
    
    @Transactional
    public CompanyEntity getCompaniesByType(CompanyEntity.CompanyType companyType) {
	return null;
    }
    
    @Transactional
    public List<CompanyEntity> getAllCompaniesOrdered(String orderBy) {
	return getAllCompaniesOrdered(orderBy, 0);
    }
    
    @Transactional
    public List<CompanyEntity> getAllCompaniesOrdered(String orderBy, int limit) {
	return null;
    }
    
    @Transactional
    public List<CompanyEntity> getCompanies() {
	return getCompanies(0);
    }
    
    @Transactional
    public List<CompanyEntity> getCompanies(int limit) {
	return null;
    }
}
