package ua.com.itinterview.service;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.com.itinterview.dao.*;
import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.*;
import ua.com.itinterview.web.command.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

public class InterviewServiceUnitTest {
    public static final String FEEDBACK_TEXT = "Feedback text";
    public static final Date CREATED_DATE = new Date(10000);
    public static final int CITY_ID = 1;
    public static final int COMPANY_ID = 2;
    public static final int POSITION_ID = 3;
    public static final int USER_ID = 4;
    public static final int TECHNOLOGY_ID = 5;
    public static final int INTERVIEW_ID = 6;
    public static final int INTERVIEW_ID_TWO =1000;
    public static final String INTERVIEW_FEEDBACK_TWO = "Feedback text two" ;

    private UserEntity user;
    private InterviewService interviewService;
    private CityDao cityDaoMock;
    private CompanyDao companyDaoMock;
    private PositionDao positionDaoMock;
    private TechnologyDao technologyDaoMock;
    private InterviewDao interviewDaoMock;
    private UserDao userDaoMock;

    @Before
    public void createMocks() {
        interviewDaoMock = EasyMock.createMock(InterviewDao.class);
        cityDaoMock=EasyMock.createMock(CityDao.class);
        companyDaoMock=EasyMock.createMock(CompanyDao.class);
        positionDaoMock=EasyMock.createMock(PositionDao.class);
        technologyDaoMock=EasyMock.createMock(TechnologyDao.class);
        userDaoMock = EasyMock.createMock(UserDao.class);

        interviewService = new InterviewService();

        interviewService.interviewEntityDao = interviewDaoMock;
        interviewService.cityDao=cityDaoMock;
        interviewService.companyDao=companyDaoMock;
        interviewService.positionDao=positionDaoMock;
        interviewService.technologyDao=technologyDaoMock;
        interviewService.userDao = userDaoMock;
    }

    @After
    public void verifyAll() {
        verify(interviewDaoMock);
    }

    public void replayAllMocks() {
        replay(interviewDaoMock);
    }

    @Test
    public void testAddInterview() {
        InterviewCommand interviewCommand = createInterviewCommand();
        InterviewEntity interviewEntity = new InterviewEntity(interviewCommand);

        EasyMock.expect(cityDaoMock.getEntityById(interviewEntity.getCity().getId()))
                .andReturn(interviewEntity.getCity());
        EasyMock.expect(companyDaoMock.getEntityById(interviewEntity.getCompany().getId()))
                .andReturn(interviewEntity.getCompany());
        EasyMock.expect(positionDaoMock.getEntityById(interviewEntity.getPosition().getId()))
                .andReturn(interviewEntity.getPosition());
        EasyMock.expect(technologyDaoMock.getEntityById(interviewEntity.getTechnology().getId()))
                .andReturn(interviewEntity.getTechnology());
        EasyMock.expect(userDaoMock.getEntityById(interviewEntity.getUser().getId()))
                .andReturn(interviewEntity.getUser());

        EasyMock.expect(interviewDaoMock.save(isA(InterviewEntity.class)))
                .andReturn(interviewEntity);

        replayAllMocks();

        assertEquals(interviewEntity,interviewService.addInterview(interviewCommand));
        verifyAll();
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
        verify(interviewDaoMock);
    }

    @Test
    public void testGetUserInterviewList() {
        List<InterviewEntity> interviewEntities = getInterviewEntities();
        Capture<UserEntity> userEntityCapture = new Capture<UserEntity>();
        Capture<PagingFilter> pagingFilterCapture = new Capture<PagingFilter>();
        expect(interviewDaoMock.getInterviewsByUser(capture(userEntityCapture), capture(pagingFilterCapture))).andReturn(interviewEntities);
        replayAllMocks();
        UserCommand userCommand = createUserCommand();
        List<InterviewCommand> actualInterviewCommands = interviewService.getUserInterviewList(userCommand);
        List<InterviewCommand> expectedInterviewCommands = getInterviewCommands();
        assertEquals(expectedInterviewCommands, actualInterviewCommands);
        UserEntity actualUserEntity = userEntityCapture.getValue();
        UserEntity expectedUserEntity = createUserEntity();
        assertEquals(expectedUserEntity, actualUserEntity);
        assertEquals(expectedUserEntity.getId(), actualUserEntity.getId());
    }

    @Test
    public void testGetUserInterviewListWithUserId() {
        List<InterviewEntity> interviewEntities = getInterviewEntities();
        Capture<UserEntity> userEntityCapture = new Capture<UserEntity>();
        Capture<PagingFilter> pagingFilterCapture = new Capture<PagingFilter>();
        expect(interviewDaoMock.getInterviewsByUser(capture(userEntityCapture), capture(pagingFilterCapture))).andReturn(interviewEntities);
        replayAllMocks();
        UserCommand userCommand = createUserCommand();
        List<InterviewCommand> actualInterviewCommands = interviewService.getUserInterviewList(userCommand.getId());
        List<InterviewCommand> expectedInterviewCommands = getInterviewCommands();
        assertEquals(expectedInterviewCommands, actualInterviewCommands);
        UserEntity actualUserEntity = userEntityCapture.getValue();
        UserEntity expectedUserEntity = createUserEntity();
        assertEquals(expectedUserEntity, actualUserEntity);
        assertEquals(expectedUserEntity.getId(), actualUserEntity.getId());
    }

    private List<InterviewCommand> getInterviewCommands() {
        List<InterviewCommand> expectedInterviewCommands = new ArrayList<InterviewCommand>();
        expectedInterviewCommands.add(createInterviewCommand());
        return expectedInterviewCommands;
    }

    private List<InterviewEntity> getInterviewEntities() {
        List<InterviewEntity> interviewEntities = new ArrayList<InterviewEntity>();
        interviewEntities.add(createInterviewEntity());
        return interviewEntities;
    }

    @Test
    public void testConvertCommandToEntity() {
        replayAllMocks();
        InterviewCommand command = createInterviewCommand();
        InterviewEntity expectedEntity = createInterviewEntity();
        InterviewEntity actualEntity = new InterviewEntity(command);
        assertEquals(expectedEntity, actualEntity);
    }

    @Test
    public void testConvertEntityToCommand() {
        replayAllMocks();
        InterviewEntity interviewEntity = createInterviewEntity();
        InterviewCommand expectedCommand = createInterviewCommand();
        InterviewCommand actualCommand = new InterviewCommand(interviewEntity);
        assertEquals(expectedCommand, actualCommand);
    }

    @Test
    public void testGetInterviewById(){
        InterviewEntity testInterviewEntity= createInterviewEntity();
        InterviewCommand expectedInterviewCommand = new InterviewCommand(testInterviewEntity);
        expect(interviewDaoMock.getEntityById(testInterviewEntity.getId())).andReturn(testInterviewEntity);
        replayAllMocks();
        InterviewCommand actualInterviewCommand = interviewService.getInterviewById(testInterviewEntity.getId());
        assertEquals(expectedInterviewCommand,actualInterviewCommand);
        verifyAll();
    }
    @Test
    public void testUpdateInterview(){
        InterviewCommand interviewCommand = createInterviewCommand();
        InterviewEntity interviewEntity = new InterviewEntity(interviewCommand);

        EasyMock.expect(interviewDaoMock.getEntityById(interviewCommand.getId()))
                .andReturn(interviewEntity);
        EasyMock.expect(cityDaoMock.getEntityById(interviewEntity.getCity().getId()))
                .andReturn(interviewEntity.getCity());
        EasyMock.expect(companyDaoMock.getEntityById(interviewEntity.getCompany().getId()))
                .andReturn(interviewEntity.getCompany());
        EasyMock.expect(positionDaoMock.getEntityById(interviewEntity.getPosition().getId()))
                .andReturn(interviewEntity.getPosition());
        EasyMock.expect(technologyDaoMock.getEntityById(interviewEntity.getTechnology().getId()))
                .andReturn(interviewEntity.getTechnology());
        EasyMock.expect(userDaoMock.getEntityById(interviewEntity.getUser().getId()))
                .andReturn(interviewEntity.getUser());

        EasyMock.expect(interviewDaoMock.save(interviewEntity))
                .andReturn(interviewEntity);
        replayAllMocks();
        interviewCommand.setFeedback(INTERVIEW_FEEDBACK_TWO);

        assertEquals(interviewEntity, interviewService.update(interviewCommand));
        verifyAll();
    }

    private InterviewCommand createInterviewCommand() {
        InterviewCommand interviewCommand = new InterviewCommand();
        interviewCommand.setId(INTERVIEW_ID);
        interviewCommand.setFeedback(FEEDBACK_TEXT);
        interviewCommand.setCreated(CREATED_DATE);
        interviewCommand.setCity(createCityCommand());
        interviewCommand.setCompany(createCompanyCommand());
        interviewCommand.setPosition(createPositionCommand());
        interviewCommand.setTechnology(createTechnologyCommand());
        interviewCommand.setUser(createUserCommand());
        return interviewCommand;
    }

    private InterviewEntity createInterviewEntity() {
        InterviewEntity interviewEntity = new InterviewEntity();
        interviewEntity.setId(INTERVIEW_ID);
        interviewEntity.setFeedback(FEEDBACK_TEXT);
        interviewEntity.setCreated(CREATED_DATE);
        interviewEntity.setCity(createCityEntity());
        interviewEntity.setCompany(createCompanyEntity());
        interviewEntity.setPosition(createPositionEntity());
        interviewEntity.setTechnology(createTechnologyEntity());
        interviewEntity.setUser(createUserEntity());
        return interviewEntity;
    }

    public CityCommand createCityCommand() {
        CityCommand cityCommand = new CityCommand();
        cityCommand.setId(CITY_ID);
        return cityCommand;
    }

    public CompanyCommand createCompanyCommand() {
        CompanyCommand companyCommand = new CompanyCommand();
        companyCommand.setId(COMPANY_ID);
        return companyCommand;
    }

    public PositionCommand createPositionCommand() {
        PositionCommand positionCommand = new PositionCommand();
        positionCommand.setId(POSITION_ID);
        return positionCommand;
    }

    public UserCommand createUserCommand() {
        UserCommand userCommand = new UserCommand();
        userCommand.setId(USER_ID);
        return userCommand;
    }

    public TechnologyCommand createTechnologyCommand() {
        TechnologyCommand technologyCommand = new TechnologyCommand();
        technologyCommand.setId(TECHNOLOGY_ID);
        return technologyCommand;
    }

    public CityEntity createCityEntity() {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setId(CITY_ID);
        return cityEntity;
    }

    public CompanyEntity createCompanyEntity() {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(COMPANY_ID);
        return companyEntity;
    }

    public PositionEntity createPositionEntity() {
        PositionEntity positionEntity = new PositionEntity();
        positionEntity.setId(POSITION_ID);
        return positionEntity;
    }

    public UserEntity createUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        return userEntity;
    }

    public TechnologyEntity createTechnologyEntity() {
        TechnologyEntity technologyEntity = new TechnologyEntity();
        technologyEntity.setId(TECHNOLOGY_ID);
        return technologyEntity;
    }
}
