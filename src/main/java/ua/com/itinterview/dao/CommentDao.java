package ua.com.itinterview.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.CommentEntity;
import ua.com.itinterview.entity.QuestionEntity;

public class CommentDao extends EntityWithIdDao<CommentEntity> {

    @Transactional
    public List<CommentEntity> getCommentsForQuestionsOrderedByRate(
	    QuestionEntity questionEntity, PagingFilter pagingFilter) {
	Criteria criteria = sessionFactory.getCurrentSession()
		.createCriteria(CommentEntity.class)
		.add(Restrictions.eq("questionEntity", questionEntity))
		.addOrder(Order.desc("rate"));
	return getResultWithPaginator(criteria, pagingFilter);
    }
}
