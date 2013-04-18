package ua.com.itinterview.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.com.itinterview.service.FeedbackService;
import ua.com.itinterview.web.command.FeedbackCommand;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackResource {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> addFeedback(@Valid @ModelAttribute FeedbackCommand feedbackCommand, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            return bindingResultToMap(bindResult);
        }
        feedbackService.addFeedback(feedbackCommand);
        return new HashMap<String, Object>();
    }

    private Map<String, Object> bindingResultToMap(BindingResult bindingResult) {
        Map<String, Object> fieldErrors = new HashMap<String, Object>(bindingResult.getErrorCount());
        for (FieldError error : bindingResult.getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        return fieldErrors;
    }
}