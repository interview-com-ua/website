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
	interview.setUserId(user.getId());
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
	inter1.setUserId(user.getId());
	inter2.setUserId(user.getId());
	interDao.save(inter1);
	interDao.save(inter2);
	// When
	List<InterviewEntity> list = interDao.getInterviewsByUser(user);
	// Then
	assertEquals(2, list.size());
	assertEquals(user.getId(), list.get(0).getUserId().intValue());
	assertEquals(user.getId(), list.get(1).getUserId().intValue());
    }

}
