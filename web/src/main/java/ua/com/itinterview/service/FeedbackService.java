package ua.com.itinterview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.FeedbackDao;
import ua.com.itinterview.entity.FeedbackEntity;
import ua.com.itinterview.web.command.FeedbackCommand;

public class FeedbackService {

    @Autowired
    FeedbackDao feedbackDao;

    public List<FeedbackCommand> getFeedbackList() {
        List<FeedbackEntity> feedbackDaoAll = feedbackDao.getAll();
        return convertToFeedbackCommandList(feedbackDaoAll);
    }

    private List<FeedbackCommand> convertToFeedbackCommandList(
            List<FeedbackEntity> feedbackEntities) {
        List<FeedbackCommand> result = new ArrayList<FeedbackCommand>();
        for (FeedbackEntity feedbackEntity : feedbackEntities) {
            result.add(new FeedbackCommand(feedbackEntity));
        }
        return result;
    }

    public void addFeedback(FeedbackCommand feedbackInterview) {
        FeedbackEntity feedbackentity = new FeedbackEntity(feedbackInterview);
        feedbackDao.save(feedbackentity);
    }

}
