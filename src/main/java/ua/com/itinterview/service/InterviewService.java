package ua.com.itinterview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.dao.CityDao;
import ua.com.itinterview.dao.InterviewDao;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.web.command.InterviewCommand;

public class InterviewService {
    @Autowired
    InterviewDao interviewEntityDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CityDao cityDao;

    public InterviewEntity addInterview(InterviewCommand inputInterview) {
	InterviewEntity interviewEntity = new InterviewEntity(inputInterview);
	InterviewEntity interview = interviewEntityDao.save(interviewEntity);
	return interview;
    }

    @Transactional
    public List<InterviewCommand> getInterviewList() {
	List<InterviewCommand> interviewList = convertToInterviewCommandList(interviewEntityDao
		.getAll());
	return interviewList;

    }

    private List<InterviewCommand> convertToInterviewCommandList(
	    List<InterviewEntity> interviewEntities) {
	List<InterviewCommand> result = new ArrayList<InterviewCommand>();
	for (InterviewEntity interviewEntity : interviewEntities) {
	    result.add(new InterviewCommand(interviewEntity));
	}
	return result;
    }
}
