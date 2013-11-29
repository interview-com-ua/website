package ua.com.itinterview.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.itinterview.service.FeedbackService;
import ua.com.itinterview.service.UserService;
import ua.com.itinterview.web.command.FeedbackCommand;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/dev")
public class TestController {
    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String showFeedbackList(Map<String, Object> map) {
        List<FeedbackCommand> feedbackList = feedbackService.getFeedbackList();
        map.put("feedbackList", feedbackList);
        return "show_feedback_list";
    }

    @RequestMapping(value = "/cleanDb", method = RequestMethod.GET)
    @ResponseBody
    public String cleanDb() {
        userService.deleteAll();
        return "OK";
    }
}
