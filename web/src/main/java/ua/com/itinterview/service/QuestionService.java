package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.dao.InterviewDao;
import ua.com.itinterview.dao.QuestionDao;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.QuestionCommand;

import java.util.ArrayList;
import java.util.List;

public class QuestionService {

    @Autowired
    InterviewDao interviewDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserDao userDao;

    public QuestionCommand getQuestionById(Integer questionId) {
	QuestionEntity oneQuestionbyId = questionDao.getEntityById(questionId);
	return new QuestionCommand(oneQuestionbyId);

    }

    public List<QuestionCommand> getQuestionListForUser(String email){
        UserEntity userEntity = userDao.getUserByEmail(email);
        List<QuestionEntity> userEntityList = questionDao.getQuestionsForUser(userEntity);
        return convertToQuestionCommandList(userEntityList);
    }

    public List<QuestionCommand> getQuestionListForInterview(Integer interviewId) {
	InterviewEntity interview = interviewDao.getEntityById(interviewId);
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

	InterviewEntity interview = interviewDao.getEntityById(interviewId);

	QuestionEntity questionEntity = new QuestionEntity(question);
	questionEntity.setInterview(interview);

	questionEntity = questionDao.save(questionEntity);

	return new QuestionCommand(questionEntity);
    }

    public QuestionCommand updateQuestion(Integer questionId,
	    QuestionCommand inputQuestion) {
	QuestionEntity questionEntity = questionDao.getEntityById(questionId);
	questionEntity.setQuestion(inputQuestion.getQuestion());
	questionEntity.setAnswer(inputQuestion.getAnswer());
	questionEntity.setTitle(inputQuestion.getTitle());
	questionEntity = questionDao.save(questionEntity);

	return new QuestionCommand(questionEntity);
    }

    public List<QuestionCommand> getRecentQuestionList() {

	List<QuestionEntity> questions = questionDao
		.getRecentQuestionList();
	return convertToQuestionCommandList(questions);
    }

}
