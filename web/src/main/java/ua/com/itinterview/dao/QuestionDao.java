package ua.com.itinterview.dao;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.configuration.ItemsPerPageConstantConfiguration;

public class QuestionDao extends EntityWithIdDao<QuestionEntity> {

    @SuppressWarnings("unchecked")
    @Transactional
    public List<QuestionEntity> getQuestionsForUser(UserEntity user) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(QuestionEntity.class);
	criteria.createAlias("interview", "i");
	criteria.add(eq("i.user", user));
	return criteria.list();

    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<QuestionEntity> getQuestionsForInterview(
	    InterviewEntity interview) {
	Session session = sessionFactory.getCurrentSession();
	Criteria criteria = session.createCriteria(QuestionEntity.class);
	criteria.add(eq("interview", interview));
	return criteria.list();
    }

    @Transactional Long getQuestionsCountForInterview(InterviewEntity interview) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(QuestionEntity.class);
        criteria.add(eq("interview", interview));
        return ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult());
    }
    
    @Transactional
    public List<QuestionEntity> getRecentQuestionList(){
	return getAllOrderedBy(
		"created",
		"desc",
		ItemsPerPageConstantConfiguration.ITEMS_PER_SEARCH_QUESTIONS_PAGE);
    }

}
