package ua.com.itinterview.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QuestionDaoIntegrationTest extends
        BaseEntityWithIdDaoIntegrationTest<QuestionEntity> {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Override
    protected QuestionEntity createEntity() {
        QuestionEntity qustion = new QuestionEntity();
        qustion.setQuestion("What is DAO?");
        return qustion;
    }

    @Override
    protected EntityWithIdDao<QuestionEntity> getEntityWithIdDao() {
        return questionDao;
    }

    @Test
    public void testGetQuestionsForUser() {
        // Given
        UserEntity user = new UserEntity();
        user.setUserName("userName");
        user = userDao.save(user);
        QuestionEntity question = new QuestionEntity();
        question.setUserId(user.getId());
        questionDao.save(question);
        // When
        List<QuestionEntity> questions = questionDao.getQuestionsForUser(user);
        // Then
        assertEquals(1, questions.size());
        QuestionEntity questionEntity = questions.get(0);
        assertEquals(user.getId(), questionEntity.getUserId().intValue());
    }

}
