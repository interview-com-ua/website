package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.PositionDao;
import ua.com.itinterview.entity.PositionEntity;
import ua.com.itinterview.web.command.PositionCommand;

public class PositionServiceUnitTest {

    private static final String positionName = "position";
    private static final String positionGroup = "position group";

    private PositionService positionService;
    private PositionDao positionDao;

    @Before
    public void setupMocks() {

	positionDao = EasyMock.createMock(PositionDao.class);
	positionService = new PositionService();
	positionService.positionDao = positionDao;
    }

    private void replayMocks() {
	EasyMock.replay(positionDao);
    }

    @After
    public void verifyMocks() {
	EasyMock.verify(positionDao);
    }

    private List<PositionEntity> generatePositionList() {

	List<PositionEntity> positions = new ArrayList<PositionEntity>();

	for (int i = 1; i <= 3; i++) {
	    PositionEntity position = new PositionEntity();
	    position.setPositionName(positionName + i);
	    position.setPositionGroup(positionGroup + i);
	    positions.add(position);
	}

	return positions;
    }

    private List<PositionCommand> convertToCommandList(
	    List<PositionEntity> entities) {

	List<PositionCommand> positions = new ArrayList<PositionCommand>();

	for (PositionEntity entity : entities) {
	    positions.add(new PositionCommand(entity));
	}

	return positions;
    }

    private PositionCommand createTestPositionCommand() {

	PositionCommand position = new PositionCommand();
	position.setName(positionName);
	position.setGroup(positionGroup);

	return position;
    }

    private PositionEntity createTestPositionEntity() {

	PositionEntity position = new PositionEntity();
	position.setPositionName(positionName);
	position.setPositionGroup(positionGroup);

	return position;
    }

    @Test
    public void testConvertEntityToCommand() {

	replayMocks();
	PositionCommand expected = createTestPositionCommand();
	assertEquals(expected, new PositionCommand(createTestPositionEntity()));
    }

    @Test
    public void testGetPositionList() {

	List<PositionEntity> positions = generatePositionList();

	EasyMock.expect(positionDao.getAllOrderedBy("positionName", 0))
		.andReturn(positions);
	replayMocks();
	assertEquals(convertToCommandList(positions),
		positionService.getPositionList());
    }

}
