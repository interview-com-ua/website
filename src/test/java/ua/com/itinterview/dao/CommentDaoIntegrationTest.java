package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.CommentEntity;

public class CommentDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<CommentEntity> {

    @Autowired
    private CommentDao commentDao;

    @Override
    protected EntityWithIdDao<CommentEntity> getEntityWithIdDao() {
	return commentDao;
    }

    @Override
    protected CommentEntity createEntity() {
	CommentEntity entity = new CommentEntity();
	entity.setAuthorName("");
	entity.setCommentText("");
	entity.setQuestionId(1);

	return entity;
    }

    @Test
    public void testGetCommentsForQuestionsOrderByRate() {
	CommentEntity entity = commentDao.save(createEntity());
	entity.setRate(3);
	CommentEntity entity1 = commentDao.save(createEntity());
	entity1.setRate(2);
	CommentEntity entity2 = commentDao.save(createEntity());
	entity2.setRate(2);

	List<CommentEntity> actualList = commentDao
		.getCommentsForQuestionsOrderedByRate(1, new PagingFilter());

	assertEquals(3, actualList.size());
	assertEquals(entity, actualList.get(0));
	assertEquals(entity1, actualList.get(1));
	assertEquals(entity2, actualList.get(2));
    }

    @Test
    public void testCommentsPagination() {
	CommentEntity entity = commentDao.save(createEntity());
	PagingFilter filter = new PagingFilter(0, 1);

	List<CommentEntity> firstPageList = commentDao
		.getCommentsForQuestionsOrderedByRate(1, filter);
	assertEquals(1, firstPageList.size());
	assertEquals(entity, firstPageList.get(0));

    }
}
