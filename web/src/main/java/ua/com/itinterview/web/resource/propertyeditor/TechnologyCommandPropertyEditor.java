package ua.com.itinterview.web.resource.propertyeditor;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.service.TechnologyService;
import ua.com.itinterview.web.resource.InterviewResource;

import java.beans.PropertyEditorSupport;

public class TechnologyCommandPropertyEditor extends PropertyEditorSupport {


    public TechnologyCommandPropertyEditor(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    private TechnologyService technologyService;

    @Override
    public void setAsText(String text) {
        setValue(technologyService.getTechnologyById(Integer.valueOf(text)));
    }
}
