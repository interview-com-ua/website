package ua.com.itinterview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.InterviewEntityDao;
import ua.com.itinterview.dao.QuestionDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.web.command.QuestionCommand;

public class QuestionService {

    @Autowired
    InterviewEntityDao interviewDao;
    @Autowired
    QuestionDao questionDao;

    public QuestionCommand getQuestionById(Integer questionId) {
	QuestionEntity oneQuestionbyId = questionDao.getOneResultByParameter(
		"id", questionId);
	return new QuestionCommand(oneQuestionbyId);

    }

    public List<QuestionCommand> getQuestionListForInterview(Integer interviewId) {
	InterviewEntity interview = interviewDao.getOneResultByParameter("id",
		interviewId);
	List<QuestionEntity> questionsForInterview = questionDao
		.getQuestionsForInterview(interview);
	return convertToQuestionCommandList(questionsForInterview);
    }

    private List<QuestionCommand> convertToQuestionCommandList(
	    List<QuestionEntity> questionEntities) {
	List<QuestionCommand> result = new ArrayList<QuestionCommand>();
	for (QuestionEntity questionEntity : questionEntities) {
	    result.add(new QuestionCommand(questionEntity));
	}
	return result;
    }

    public QuestionCommand addQuestionToInterview(Integer interviewId,
	    QuestionCommand question) {

	InterviewEntity interview = interviewDao.getOneResultByParameter("id",
		interviewId);

	QuestionEntity questionEntity = new QuestionEntity(question);
	questionEntity.setInterview(interview);

	questionEntity = questionDao.save(questionEntity);

	return new QuestionCommand(questionEntity);
    }

}
