package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.dao.*;
import ua.com.itinterview.dao.paging.PagingFilter;
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
    @Autowired
    CompanyDao companyDao;
    @Autowired
    PositionDao positionDao;
    @Autowired
    TechnologyDao technologyDao;


    public InterviewEntity addInterview(InterviewCommand inputInterview) {
        InterviewEntity interviewEntity = new InterviewEntity();
        interviewEntity.setUser(userDao.getEntityById(inputInterview.getUser().getId()));
        interviewEntity.setCompany(companyDao.getEntityById(inputInterview.getCompany().getId()));
        interviewEntity.setPosition(positionDao.getEntityById(inputInterview.getPosition().getId()));
        interviewEntity.setCity(cityDao.getEntityById(inputInterview.getCity().getId()));
        interviewEntity.setTechnology(technologyDao.getEntityById(inputInterview.getTechnology().getId()));
        interviewEntity.setFeedback(inputInterview.getFeedback());
        return  interviewEntityDao.save(interviewEntity);
    }

    public List<InterviewCommand> getInterviewList() {
        return convertToInterviewCommandList(interviewEntityDao
                .getAll());

    }

    public List<InterviewCommand> getUserInterviewList(UserCommand userCommand) {
        return getUserInterviewList(userCommand.getId());
    }

    public List<InterviewCommand> getUserInterviewList(Integer userId) {
        return getUserInterviewList(userId, null);
    }

    public List<InterviewCommand> getUserInterviewList(Integer userId, PagingFilter filter) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        List<InterviewEntity> interviewEntities = interviewEntityDao.getInterviewsByUser(user, filter);
        return convertToInterviewCommandList(interviewEntities);
    }

    public Long getInterviewsCountForUser(Integer userId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        return interviewEntityDao.getInterviewsCountByUser(user);
    }

    public Long getInterviewsCountForUser() {
        return interviewEntityDao.getCount();
    }

    private List<InterviewCommand> convertToInterviewCommandList(
            List<InterviewEntity> interviewEntities) {
        List<InterviewCommand> result = new ArrayList<InterviewCommand>(interviewEntities.size());
        for (InterviewEntity interviewEntity : interviewEntities) {
            result.add(new InterviewCommand(interviewEntity));
        }
        return result;
    }

    public InterviewCommand getInterviewById(int id) {
        return new InterviewCommand(interviewEntityDao.getEntityById(id));
    }

    public InterviewEntity update(InterviewCommand inputInterview) {
        InterviewEntity interviewEntity = interviewEntityDao.getEntityById(inputInterview.getId());
        interviewEntity.setUser(userDao.getEntityById(inputInterview.getUser().getId()));
        interviewEntity.setCompany(companyDao.getEntityById(inputInterview.getCompany().getId()));
        interviewEntity.setPosition(positionDao.getEntityById(inputInterview.getPosition().getId()));
        interviewEntity.setCity(cityDao.getEntityById(inputInterview.getCity().getId()));
        interviewEntity.setTechnology(technologyDao.getEntityById(inputInterview.getTechnology().getId()));
        interviewEntity.setFeedback(inputInterview.getFeedback());
        return   interviewEntityDao.save(interviewEntity);
    }

}
