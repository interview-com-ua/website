package ua.com.itinterview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.CommentDao;
import ua.com.itinterview.dao.QuestionDao;
import ua.com.itinterview.dao.paging.PagingFilter;
import ua.com.itinterview.entity.CommentEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.web.command.CommentCommand;
import ua.com.itinterview.web.configuration.ItemsPerPageConstantConfiguration;

public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    QuestionDao questionDao;

    public CommentCommand addCommentForQuestion(Integer questionId,
	    CommentCommand comment) {

	QuestionEntity question = questionDao.getEntityById(questionId);

	CommentEntity commentEntity = new CommentEntity(comment);
	commentEntity.setQuestionEntity(question);

	commentEntity = commentDao.save(commentEntity);

	return new CommentCommand(commentEntity);

    }

    public List<CommentCommand> getCommentListForQuestion(int questionId,
	    int currentPage) {
	PagingFilter pagingFilter = new PagingFilter(currentPage,
		ItemsPerPageConstantConfiguration.ITEMS_PER_COMMENTS_PAGE);
	QuestionEntity questionEntity = questionDao.getEntityById(questionId);
	List<CommentEntity> commentEntitiesForQuestion = commentDao
		.getCommentsForQuestionsOrderedByRate(questionEntity,
			pagingFilter);
	List<CommentCommand> commentCommandsForQuestion = new ArrayList<CommentCommand>();

	for (int i = 0; i < commentEntitiesForQuestion.size(); i++) {
	    CommentCommand commentCommand = new CommentCommand(
		    commentEntitiesForQuestion.get(i));
	    commentCommandsForQuestion.add(commentCommand);
	}
	return commentCommandsForQuestion;
    }

}
