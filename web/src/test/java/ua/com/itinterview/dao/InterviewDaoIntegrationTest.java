package ua.com.itinterview.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.entity.*;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InterviewDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<InterviewEntity> {

    @Autowired
    private UserDao userDao;
    @Autowired
    private InterviewDao interDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private TechnologyDao technologyDao;
    @Autowired
    private PositionDao positionDao;
    @Autowired
    private CompanyDao companyDao;

    @Override
    protected InterviewEntity createEntity() {
	InterviewEntity interview = new InterviewEntity();
	interview.setFeedback("jkjh");
	interview.setCreated(new Date());
	interview.setUser(testUser);
	return interview;
    }

    @Override
    protected EntityWithIdDao<InterviewEntity> getEntityWithIdDao() {
	return interDao;
    }

    @Test
    public void testGetInterviewsByUser() {
	UserEntity interviewAuthor = new UserEntity();
	interviewAuthor = userDao.save(interviewAuthor);
	InterviewEntity interview1 = new InterviewEntity();
	InterviewEntity interview2 = new InterviewEntity();
	interview1.setUser(interviewAuthor);
	interview2.setUser(interviewAuthor);
	interDao.save(interview1);
	interDao.save(interview2);
	// When
	List<InterviewEntity> list = interDao
		.getInterviewsByUser(interviewAuthor);
	// Then
	assertEquals(2, list.size());
	assertEquals(interviewAuthor, list.get(0).getUser());
	assertEquals(interviewAuthor, list.get(1).getUser());
	System.out.println("ID NUMBER OF INTERVIEW : " + list.get(0).getId());
	System.out.println("ID NUMBER OF INTERVIEW : " + list.get(1).getId());
    }

    @Test
    public void testGetInterviewById() {
	UserEntity interviewAuthor = new UserEntity();
	interviewAuthor = userDao.save(interviewAuthor);
	InterviewEntity interview1 = new InterviewEntity();
	InterviewEntity interview2 = new InterviewEntity();
	interview1.setUser(interviewAuthor);
	interview2.setUser(interviewAuthor);
	interDao.save(interview1);
	interDao.save(interview2);
	Integer id = interDao.getInterviewsByUser(interviewAuthor).get(0)
		.getId();
	System.out.println("ID NUMBER OF INTERVIEW : " + id);
	InterviewEntity expected = interDao.getEntityById(id);
	assertEquals(interviewAuthor, expected.getUser());
    }

    @Test
    public void testSaveInterviewWithCityTechnologyPositionCompany() {
	CityEntity city = cityDao.save(createCityByParams(0, "cityName"));
	TechnologyEntity technology = technologyDao
		.save(createTechnologyByParams(0, "technologyName"));
	PositionEntity position = positionDao.save(createPositionByParams(0,
		"positionName"));
	CompanyEntity company = companyDao.save(createCompanyByParams(0,
		"companyName"));

	InterviewEntity interview = new InterviewEntity();
	interview.setUser(testUser);
	interview.setCompany(createCompanyByParams(company.getId(), null));
	interview.setTechnology(createTechnologyByParams(technology.getId(),
		null));
	interview.setPosition(createPositionByParams(position.getId(), null));
	interview.setCity(createCityByParams(city.getId(), null));

	interview = interDao.save(interview);
	assertEquals(city, interview.getCity());
	assertEquals(technology, interview.getTechnology());
	assertEquals(company, interview.getCompany());
	assertEquals(position, interview.getPosition());
    }

    private CityEntity createCityByParams(int id, String cityName) {
	CityEntity city = new CityEntity();
	city.setId(id);
	city.setCityName(cityName);
	return city;
    }

    private PositionEntity createPositionByParams(int id, String positionName) {
	PositionEntity position = new PositionEntity();
	position.setId(id);
	position.setPositionName(positionName);
	return position;
    }

    private CompanyEntity createCompanyByParams(int id, String companyName) {
	CompanyEntity company = new CompanyEntity();
	company.setId(id);
	company.setCompanyName(companyName);
	return company;
    }

    private TechnologyEntity createTechnologyByParams(int id,
	    String technologyName) {
	TechnologyEntity technology = new TechnologyEntity();
	technology.setId(id);
	technology.setTechnologyName(technologyName);
	return technology;
    }

}
