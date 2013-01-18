package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;

public class InterviewDaoTest extends
	BaseEntityWithIdDaoIntegrationTest<InterviewEntity> {

    @Autowired
    private UserDao userDao;
    @Autowired
    private InterviewEntityDao interDao;

    @Override
    protected InterviewEntity createEntity() {
	InterviewEntity interview = new InterviewEntity();
	interview.setFeedback("jkjh");
	interview.setCreated(new Date());
	UserEntity user = new UserEntity();
	user.setEmail("dsakdj");
	user.setPassword("vcndt");
	userDao.save(user);
	interview.setUser(user);
	return interview;
    }

    @Override
    protected EntityWithIdDao<InterviewEntity> getEntityWithIdDao() {
	return interDao;
    }

    @Test
    public void testGetInterviewsByUser() {
	UserEntity user = new UserEntity();
	userDao.save(user);
	InterviewEntity inter1 = new InterviewEntity();
	InterviewEntity inter2 = new InterviewEntity();
	inter1.setUser(user);
	inter2.setUser(user);
	interDao.save(inter1);
	interDao.save(inter2);
	// When
	List<InterviewEntity> list = interDao.getInterviewsByUser(user);
	// Then
	assertEquals(2, list.size());
	assertEquals(user, list.get(0).getUser());
	assertEquals(user, list.get(1).getUser());
    }

}
