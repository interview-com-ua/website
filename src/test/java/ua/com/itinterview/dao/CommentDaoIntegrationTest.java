package ua.com.itinterview.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.CommentEntity;
import ua.com.itinterview.entity.QuestionEntity;

public class CommentDaoIntegrationTest extends
	BaseEntityWithIdDaoIntegrationTest<CommentEntity> {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private QuestionDao questionDao;
    private QuestionEntity questionEntity;

    @Override
    protected EntityWithIdDao<CommentEntity> getEntityWithIdDao() {
	return commentDao;
    }

    @Override
    protected CommentEntity createEntity() {
	CommentEntity entity = new CommentEntity();
	entity.setQuestionEntity(questionEntity);
	entity.setAuthorName("");
	entity.setCommentText("");
	return entity;
    }

    @Before
    public void createQuestionEntity() {
	questionEntity = new QuestionEntity();
	questionEntity = questionDao.save(questionEntity);

    }

    @Test
    public void testGetCommentsForQuestionsOrderByRate() {
        //Given
	CommentEntity entity = new CommentEntity();
	entity.setRate(2);
	entity.setQuestionEntity(questionEntity);
	entity.setAuthorName("auther1");
	entity.setCommentText("comment text1");

	entity = commentDao.save(entity);

	CommentEntity entity1 = new CommentEntity();
	entity1.setRate(3);
	entity1.setQuestionEntity(questionEntity);
	entity1.setAuthorName("auther2");
	entity1.setCommentText("comment text2");
	entity1 = commentDao.save(entity1);

	CommentEntity entity2 = new CommentEntity();
	entity2.setRate(1);
	entity2.setQuestionEntity(questionEntity);
	entity2.setAuthorName("auther3");
	entity2.setCommentText("comment text3");
	entity2 = commentDao.save(entity2);

        //When
	List<CommentEntity> actualList = commentDao
		.getCommentsForQuestionsOrderedByRate(questionEntity,
			new PagingFilter());

        //Then
	assertEquals(3, actualList.size());
	assertEquals(entity1, actualList.get(0));
	assertEquals(entity, actualList.get(1));
	assertEquals(entity2, actualList.get(2));
    }

    @Test
    public void testCommentsPagination() {
	CommentEntity entity = commentDao.save(createEntity());
	PagingFilter filter = new PagingFilter(0, 1);

	List<CommentEntity> firstPageList = commentDao
		.getCommentsForQuestionsOrderedByRate(questionEntity, filter);
	assertEquals(1, firstPageList.size());
	assertEquals(entity, firstPageList.get(0));

	CommentEntity entity1 = commentDao.save(createEntity());
	PagingFilter pagingFilter1 = new PagingFilter(1, 1);
	List<CommentEntity> secondPageList = commentDao
		.getCommentsForQuestionsOrderedByRate(questionEntity,
			pagingFilter1);
	assertEquals(1, secondPageList.size());
	assertEquals(entity1, secondPageList.get(0));

	PagingFilter pagingFilter2 = new PagingFilter(3, 1);
	List<CommentEntity> thirdPageList = commentDao
		.getCommentsForQuestionsOrderedByRate(questionEntity,
			pagingFilter2);
	assertEquals(0, thirdPageList.size());
    }
}
