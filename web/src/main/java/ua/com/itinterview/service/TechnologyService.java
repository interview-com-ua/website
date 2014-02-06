package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.dao.TechnologyDao;
import ua.com.itinterview.entity.TechnologyEntity;
import ua.com.itinterview.web.command.TechnologyCommand;

import java.util.ArrayList;
import java.util.List;

public class TechnologyService {

    @Autowired
    TechnologyDao technologyDao;

    public List<TechnologyCommand> getTechnologyList() {
	return convertToTechnologyCommandList(technologyDao.getAllOrderedBy("technologyName", 0));
    }

    public TechnologyCommand getTechnologyById(int idTechnology){
      return new TechnologyCommand(technologyDao.getEntityById(idTechnology));
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
