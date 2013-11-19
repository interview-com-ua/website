package ua.com.itinterview.service;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.itinterview.dao.TechnologyDao;
import ua.com.itinterview.entity.TechnologyEntity;
import ua.com.itinterview.web.command.TechnologyCommand;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TechnologyServiceUnitTest {

    private static final String TECHNOLOGY_NAME = "technology";
    private static final Integer ID = 15;
    private TechnologyService technologyService;
    private TechnologyDao technologyDao;

    @Before
    public void setupMocks() {

	technologyDao = EasyMock.createMock(TechnologyDao.class);
	technologyService = new TechnologyService();
	technologyService.technologyDao = technologyDao;
    }

    private void replayMocks() {
	EasyMock.replay(technologyDao);
    }

    @After
    public void verifyMocks() {
	EasyMock.verify(technologyDao);
    }

    private List<TechnologyEntity> generateTechnologyList() {

	List<TechnologyEntity> technologies = new ArrayList<TechnologyEntity>();

	for (int i = 1; i <= 3; i++) {
	    TechnologyEntity technology = new TechnologyEntity();
	    technology.setTechnologyName(TECHNOLOGY_NAME + i);
	    technologies.add(technology);
	}

	return technologies;
    }

    private List<TechnologyCommand> convertToCommandList(
	    List<TechnologyEntity> entities) {

	List<TechnologyCommand> technologies = new ArrayList<TechnologyCommand>();

	for (TechnologyEntity entity : entities) {
	    technologies.add(new TechnologyCommand(entity));
	}

	return technologies;
    }

    private TechnologyCommand createTestTechnologyCommand() {

	TechnologyCommand technology = new TechnologyCommand();
	technology.setName(TECHNOLOGY_NAME);
	technology.setId(ID);

	return technology;
    }

    private TechnologyEntity createTestTechnologyEntity() {

	TechnologyEntity technology = new TechnologyEntity();
	technology.setTechnologyName(TECHNOLOGY_NAME);
	technology.setId(ID);

	return technology;
    }

    @Test
    public void testConvertEntityToCommand() {

	replayMocks();
	TechnologyCommand expected = createTestTechnologyCommand();
	assertEquals(expected, new TechnologyCommand(
		createTestTechnologyEntity()));
    }

    @Test
    public void testConvertCommandToEntity() {
        replayMocks();
        TechnologyEntity expectedEntity = createTestTechnologyEntity();
        TechnologyEntity actualEntity = new TechnologyEntity(createTestTechnologyCommand());
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    public void testGetTechnologyList() {

	List<TechnologyEntity> technologies = generateTechnologyList();

	EasyMock.expect(technologyDao.getAllOrderedBy("technologyName", 0))
		.andReturn(technologies);
	replayMocks();
	assertEquals(convertToCommandList(technologies),
		technologyService.getTechnologyList());
    }

    @Test

    public void testGetTechnologyById(){
        TechnologyEntity testTechnologyEntity = createTestTechnologyEntity();

        TechnologyCommand expectedTechnologyCommand = new TechnologyCommand(testTechnologyEntity);

        EasyMock.expect(technologyDao.getEntityById(testTechnologyEntity.getId())).andReturn(testTechnologyEntity);
        replayMocks();
        assertEquals(expectedTechnologyCommand, technologyService.getTechnologyById(testTechnologyEntity.getId()));

    }

}
