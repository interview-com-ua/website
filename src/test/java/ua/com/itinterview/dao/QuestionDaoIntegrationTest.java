package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;

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
	InterviewEntity interview = createInterview();
	QuestionEntity question = new QuestionEntity();
	question.setQuestion("What is DAO?");
	question.setCreate(new Date());
	question.setInterview(interview);
	return question;
    }

    private InterviewEntity createInterview() {
	InterviewEntity interview = new InterviewEntity();
	interview.setCreated(new Date());
	interview.setFeedback("Tra-ta-ta");
	interview.setUser(testUser);
	interview = interDao.save(interview);
	return interview;
    }

    @Override
    protected EntityWithIdDao<QuestionEntity> getEntityWithIdDao() {
	return questionDao;
    }

    @Test
    public void getQuestionsForInterview() {
	InterviewEntity interview1 = createInterview();
	QuestionEntity question1 = new QuestionEntity();
	question1.setInterview(interview1);
	question1.setCreate(new Date());
	question1.setQuestion("dkjdkf");
	questionDao.save(question1);
	// When
	List<QuestionEntity> list = questionDao
		.getQuestionsForInterview(interview1);
	// Then
	assertEquals(1, list.size());
	assertEquals(question1.getQuestion(), list.get(0).getQuestion());
    }

    @Test
    public void getQuestionsForUser() {
	InterviewEntity interview = createInterview();
	QuestionEntity question = new QuestionEntity();
	question.setInterview(interview);
	question.setCreate(new Date());
	question.setQuestion("dkjdkf");
	questionDao.save(question);
	// When
	List<QuestionEntity> list = questionDao.getQuestionsForUser(testUser);
	// Then
	assertEquals(1, list.size());
	assertEquals(question, list.get(0));
    }

}
