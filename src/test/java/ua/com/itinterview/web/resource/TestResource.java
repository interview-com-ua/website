package ua.com.itinterview.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.itinterview.service.FeedbackService;
import ua.com.itinterview.web.command.FeedbackCommand;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/test")
public class TestResource {
    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String showFeedbackList(Map<String, Object> map) {
        List<FeedbackCommand> feedbackList = feedbackService.getFeedbackList();
        map.put("feedbackList", feedbackList);
        return "show_feedback_list";
    }
}