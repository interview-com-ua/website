package ua.com.itinterview.web.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class ValidatedResource {

    @Autowired
    private Validator validator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
	binder.setValidator(validator);
    }

}
