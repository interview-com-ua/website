package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;

public class QuestionDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<QuestionEntity> {

    @Autowired
    private InterviewEntityDao interDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Override
    protected QuestionEntity createEntity() {

	UserEntity user = new UserEntity();
	user.setEmail("dsakdj");
	user.setPassword("vcndt");
	user.setUserName("Vasya");
	user = userDao.save(user);

	InterviewEntity interview = new InterviewEntity();
	interview.setCreated(new Date());
	interview.setFeedback("Tra-ta-ta");
	interview.setUser(user);
	interview = interDao.save(interview);

	QuestionEntity question = new QuestionEntity();
	question.setQuestion("What is DAO?");
	question.setCreate(new Date());
	question.setInterview(interview);
	// question = questionDao.save(question);

	return question;
    }

    @Override
    protected EntityWithIdDao<QuestionEntity> getEntityWithIdDao() {
	return questionDao;
    }

    @Test
    public void getQuestionsForInterview() {
	UserEntity interviewAuthor = new UserEntity();
	interviewAuthor = userDao.save(interviewAuthor);

	InterviewEntity interview1 = new InterviewEntity();
	interview1.setUser(interviewAuthor);
	interview1 = interDao.save(interview1);

	QuestionEntity question1 = new QuestionEntity();
	question1.setInterview(interview1);
	questionDao.save(question1);

	// When
	List<QuestionEntity> list = questionDao
		.getQuestionsForInterview(interview1);

	// Then
	assertEquals(1, list.size());
	assertEquals(question1, list.get(0).getQuestion());
    }

    // @Test
    // public void testGetQuestionsForUser(){
    // InterviewEntity interview1 = new InterviewEntity();
    // interview1 = interDao.save(interview1);
    //
    // QuestionEntity question1 = new QuestionEntity();
    // QuestionEntity question2 = new QuestionEntity();
    //
    // question1.setInterview(interview1);
    // question2.setInterview(interview1);
    //
    // questionDao.save(question1);
    // questionDao.save(question2);
    //
    // UserEntity interviewAuthor = new UserEntity();
    //
    // // When
    // List<QuestionEntity> list =
    // questionDao.getQuestionsForUser(interviewAuthor);
    // // Then
    // assertEquals(2, list.size());
    // assertEquals(interview1, list.get(0).getQuestion());
    // assertEquals(interview1, list.get(1).getQuestion());
    //
    //
    // }

    // @Test
    // public void testGetQuestionsForUser() {
    // // Given
    // UserEntity user = new UserEntity();
    // user.setUserName("userName");
    // user = userDao.save(user);
    // QuestionEntity question = new QuestionEntity();
    // // question.setUserId(user.getId());
    // questionDao.save(question);
    // // When
    // List<QuestionEntity> questions = questionDao.getQuestionsForUser(user);
    // // Then
    // assertEquals(1, questions.size());
    // QuestionEntity questionEntity = questions.get(0);
    // // assertEquals(user.getId(), questionEntity.getUserId().intValue());
    // }

}
