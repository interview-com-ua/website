package ua.com.itinterview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.TechnologyDao;
import ua.com.itinterview.entity.TechnologyEntity;
import ua.com.itinterview.web.command.TechnologyCommand;

public class TechnologyService {

    @Autowired
    TechnologyDao technologyDao;

    public List<TechnologyCommand> getTechnologyList() {
	return convertToTechnologyCommandList(technologyDao.getAllOrderedBy("technologyName", 0));
    }

    private List<TechnologyCommand> convertToTechnologyCommandList(
	    List<TechnologyEntity> entities) {

	List<TechnologyCommand> technologies = new ArrayList<TechnologyCommand>();

	for (TechnologyEntity entity : entities) {
	    technologies.add(new TechnologyCommand(entity));
	}

	return technologies;
    }

}
