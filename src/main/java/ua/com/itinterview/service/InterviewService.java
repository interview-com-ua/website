package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.InterviewEntityDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.web.command.InterviewCommand;

public class InterviewService {
    @Autowired
    InterviewEntityDao interviewEntityDao;

    public void addInterview(InterviewCommand inputInterview) {
	InterviewEntity interviewEntity = new InterviewEntity(inputInterview);
	interviewEntityDao.save(interviewEntity);
    }
}
