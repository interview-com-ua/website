package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.dao.PositionDao;
import ua.com.itinterview.entity.PositionEntity;
import ua.com.itinterview.web.command.PositionCommand;

import java.util.ArrayList;
import java.util.List;

public class PositionService {

    @Autowired
    PositionDao positionDao;

    public List<PositionCommand> getPositionList() {
	return convertToPositionCommandList(positionDao.getAllOrderedBy("positionName", 0));
    }

    private List<PositionCommand> convertToPositionCommandList(
	    List<PositionEntity> entities) {

	List<PositionCommand> positions = new ArrayList<PositionCommand>();

	for (PositionEntity entity : entities) {
	    positions.add(new PositionCommand(entity));
	}

	return positions;
    }

    public PositionCommand getPositionById(int id) {
        return new PositionCommand(positionDao.getEntityById(id));
    }
}
