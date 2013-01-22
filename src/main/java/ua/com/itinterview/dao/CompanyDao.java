package ua.com.itinterview.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.CompanyEntity;

public class CompanyDao extends EntityWithIdDao<CompanyEntity> {

    @Transactional
    public CompanyEntity getCompanyByCompanyName(String companyName) {
	return getOneResultByParameter("companyName", companyName);
    }
    
    @Transactional
    public List<CompanyEntity> getCompaniesByType(
	    CompanyEntity.CompanyType companyType) {
	return getCompaniesByType(companyType, 0);
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<CompanyEntity> getCompaniesByType(
	    CompanyEntity.CompanyType companyType, int limit) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(CompanyEntity.class);
	criteria.add(Restrictions.eq("companyType", companyType))
		.setMaxResults(limit);
	List<CompanyEntity> companies = criteria.list();
	return companies;
    }

    @Transactional
    public List<CompanyEntity> getCompaniesOrdered(String orderBy) {
	return getCompaniesOrdered(orderBy, 0);
    }

    @Transactional
    public List<CompanyEntity> getCompaniesOrdered(String orderBy, int limit) {
	return getAllOrderedBy(orderBy, limit);
    }

    @Transactional
    public List<CompanyEntity> getCompanies() {
	return getCompanies(0);
    }

    @Transactional
    public List<CompanyEntity> getCompanies(int limit) {
	return getAll(limit);
    }
}
