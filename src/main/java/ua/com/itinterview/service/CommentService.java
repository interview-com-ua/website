package ua.com.itinterview.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.CommentDao;
import ua.com.itinterview.dao.QuestionDao;
import ua.com.itinterview.entity.CommentEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.web.command.CommentCommand;

public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    QuestionDao questionDao;

    public CommentCommand addCommentForQuestion(Integer questionId,
	    CommentCommand comment) {

	QuestionEntity question = questionDao.getOneResultByParameter("id",
		questionId);

	CommentEntity commentEntity = new CommentEntity(comment);
	commentEntity.setQuestionEntity(question);

	commentEntity = commentDao.save(commentEntity);

	return new CommentCommand(commentEntity);

    }

    public List<CommentCommand> getCommentListForQuestion(int questionId) {
	return null;
    }
}
