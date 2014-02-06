package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.dao.CompanyDao;
import ua.com.itinterview.entity.CompanyEntity;
import ua.com.itinterview.web.command.CompanyCommand;

import java.util.ArrayList;
import java.util.List;

public class CompanyService {

    @Autowired
    CompanyDao companyDao;

    public List<CompanyCommand> getCompanyList() {
	return convertToCompanyCommandList(companyDao.getAllOrderedBy("companyName", 0));
    }

    private List<CompanyCommand> convertToCompanyCommandList(
	    List<CompanyEntity> entities) {

	List<CompanyCommand> companies = new ArrayList<CompanyCommand>();

	for (CompanyEntity entity : entities) {
	    companies.add(new CompanyCommand(entity));
	}

	return companies;
    }

    public CompanyCommand getCompanyById(int id) {
        return new CompanyCommand(companyDao.getEntityById(id));
    }
}
