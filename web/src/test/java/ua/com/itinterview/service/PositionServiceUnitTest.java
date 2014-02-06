package ua.com.itinterview.service;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.itinterview.dao.PositionDao;
import ua.com.itinterview.entity.PositionEntity;
import ua.com.itinterview.web.command.PositionCommand;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PositionServiceUnitTest {

    private static final Integer ID = 15;
    private static final String POSITION_NAME = "position";
    private static final String POSITION_GROUP = "position group";

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
	    position.setPositionName(POSITION_NAME + i);
	    position.setPositionGroup(POSITION_GROUP + i);
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
	position.setName(POSITION_NAME);
	position.setGroup(POSITION_GROUP);
	position.setId(ID);

	return position;
    }

    private PositionEntity createTestPositionEntity() {

	PositionEntity position = new PositionEntity();
	position.setPositionName(POSITION_NAME);
	position.setPositionGroup(POSITION_GROUP);
	position.setId(ID);

	return position;
    }

    @Test
    public void testConvertEntityToCommand() {

	replayMocks();
	PositionCommand expected = createTestPositionCommand();
	assertEquals(expected, new PositionCommand(createTestPositionEntity()));
    }

    @Test
    public void testConvertCommandToEntity() {
        replayMocks();
        PositionEntity expectedEntity = createTestPositionEntity();
        PositionEntity actualEntity = new PositionEntity(createTestPositionCommand());
        assertEquals(expectedEntity, actualEntity);
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

    @Test
    public void testGetPositionById(){
        PositionEntity testPositionEntity = createTestPositionEntity();
        PositionCommand expectedPositionCommand = new PositionCommand(testPositionEntity);

        EasyMock.expect(positionDao.getEntityById(testPositionEntity.getId())).andReturn(testPositionEntity);
        replayMocks();
        assertEquals(expectedPositionCommand,positionService.getPositionById(testPositionEntity.getId()));

    }

}
