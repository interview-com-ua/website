package ua.com.itinterview.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.FeedbackDao;
import ua.com.itinterview.entity.FeedbackEntity;

public class BootStrap {

    private String environment;

    @Autowired
    FeedbackDao feedbackDao;

    public void init() {
    }

    public void destroy() {

    }

    public String getEnvironment() {
	return environment;
    }

    public void setEnvironment(String environment) {
	this.environment = environment;
    }
}