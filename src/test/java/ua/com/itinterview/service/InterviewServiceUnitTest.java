package ua.com.itinterview.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.InterviewDao;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.CityEntity;
import ua.com.itinterview.entity.CompanyEntity;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.PositionEntity;
import ua.com.itinterview.entity.TechnologyEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.InterviewCommand;

public class InterviewServiceUnitTest {

    private static final int COMPANY_ID = 30;
    private static final int POSITION_ID = 32;
    private static final int TECHNOLOGY_ID = 11;
    private static final int CITY_ID = 10;
    private UserEntity user;
    private InterviewService interviewService;
    private InterviewDao interviewDaoMock;
    private UserDao userDao;

    @Before
    public void createMocks() {
	interviewDaoMock = EasyMock.createMock(InterviewDao.class);
	interviewService = new InterviewService();
	interviewService.interviewEntityDao = interviewDaoMock;
	user = new UserEntity();
	userDao = EasyMock.createMock(UserDao.class);
	interviewService.userDao = userDao;
    }

    @Test
    public void testAddInterview() {
	InterviewCommand interviewCommand = createInterviewCommand();
	InterviewEntity interviewEntity = new InterviewEntity(interviewCommand);
	// EasyMock.expect(
	// interviewDaoMock
	// .getInterviewsByUser(interviewCommand.getUser()).size())
	// .andReturn(1);
	EasyMock.expect(interviewDaoMock.save(interviewEntity)).andReturn(
		interviewEntity);
	EasyMock.replay(interviewDaoMock);
	interviewService.addInterview(interviewCommand);
	EasyMock.verify(interviewDaoMock);
    }

    @Test
    public void testConvertCommandToEntity() {
	InterviewCommand command = createInterviewCommand();
	InterviewEntity expected = createInterviewEntity();
	InterviewEntity actual = new InterviewEntity(command);
	assertEquals(expected, actual);
    }

    @Test
    public void testConvertEntityToCommand() {
	InterviewEntity interviewEntity = createInterviewEntity();
	InterviewCommand expectedCommand = createInterviewCommand();
	InterviewCommand actualCommand = new InterviewCommand(interviewEntity);
	assertEquals(expectedCommand, actualCommand);
    }

    private InterviewCommand createInterviewCommand() {
	InterviewCommand interviewCommand = new InterviewCommand();
	interviewCommand.setFeedback("test");
	interviewCommand.setCreated(new Date(10000));
	interviewCommand.setId(1);
	user.setEmail("email");
	user.setPassword("password");
	user.setUserName("name");
	interviewCommand.setUser(user);
	interviewCommand.setCityId(CITY_ID);
	interviewCommand.setTechnologyId(TECHNOLOGY_ID);
	interviewCommand.setPositionId(POSITION_ID);
	interviewCommand.setCompanyId(COMPANY_ID);
	return interviewCommand;
    }

    private InterviewEntity createInterviewEntity() {
	InterviewEntity interviewEntity = new InterviewEntity();
	interviewEntity.setFeedback("test");
	interviewEntity.setCreated(new Date(10000));
	interviewEntity.setId(1);
	user.setEmail("email");
	user.setPassword("password");
	user.setUserName("name");
	userDao.save(user);
	interviewEntity.setUser(user);
	CityEntity city = new CityEntity();
	city.setId(CITY_ID);
	interviewEntity.setCity(city);
	TechnologyEntity technology = new TechnologyEntity();
	technology.setId(TECHNOLOGY_ID);
	interviewEntity.setTechnology(technology);
	PositionEntity position = new PositionEntity();
	position.setId(POSITION_ID);
	interviewEntity.setPosition(position);
	CompanyEntity company = new CompanyEntity();
	company.setId(COMPANY_ID);
	interviewEntity.setCompany(company);
	return interviewEntity;
    }

    @Test
    public void testGetInterviewList() {
	List<InterviewEntity> list = new ArrayList<InterviewEntity>();
	InterviewEntity interviewEntity = createInterviewEntity();
	System.out.println(interviewEntity.getUser());
	list.add(interviewEntity);
	EasyMock.expect(interviewDaoMock.getAll()).andReturn(list);
	EasyMock.replay(interviewDaoMock);
	interviewService.getInterviewList();
	EasyMock.verify(interviewDaoMock);
    }

}
