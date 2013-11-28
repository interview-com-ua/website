package ua.com.itinterview.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.com.itinterview.service.UserService;

@Controller
public class TestUtilsResource {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/cleanDb", method = RequestMethod.GET)
    @ResponseBody
    public String cleanDb() {
        userService.deleteAll();
        return "OK";
    }
}
