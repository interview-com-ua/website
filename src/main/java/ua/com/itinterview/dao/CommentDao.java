package ua.com.itinterview.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.CommentEntity;

public class CommentDao extends EntityWithIdDao<CommentEntity> {

    @SuppressWarnings("unchecked")
    @Transactional
    public List<CommentEntity> getCommentsForQuestionsOrderedByRate(
	    int questionId, PagingFilter pagingFilter) {
	Criteria criteria = sessionFactory.getCurrentSession()
		.createCriteria(CommentEntity.class)
		.add(Restrictions.eq("questionId", questionId))
		.addOrder(Order.desc("rate"));
	return criteria.list();
    }
}
