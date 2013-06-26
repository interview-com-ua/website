package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.dao.CityDao;
import ua.com.itinterview.dao.InterviewDao;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.command.InterviewCommand;
import ua.com.itinterview.web.command.UserCommand;

import java.util.ArrayList;
import java.util.List;

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

    public List<InterviewCommand> getInterviewList() {
        List<InterviewCommand> interviewList = convertToInterviewCommandList(interviewEntityDao
                .getAll());
        return interviewList;

    }

    public List<InterviewCommand> getUserInterviewList(UserCommand userCommand) {
        List<InterviewEntity> interviewEntities = interviewEntityDao.getInterviewsByUser(new UserEntity(userCommand));
        return convertToInterviewCommandList(interviewEntities);
    }

    private List<InterviewCommand> convertToInterviewCommandList(
            List<InterviewEntity> interviewEntities) {
        List<InterviewCommand> result = new ArrayList<InterviewCommand>(interviewEntities.size());
        for (InterviewEntity interviewEntity : interviewEntities) {
            result.add(new InterviewCommand(interviewEntity));
        }
        return result;
    }
}
