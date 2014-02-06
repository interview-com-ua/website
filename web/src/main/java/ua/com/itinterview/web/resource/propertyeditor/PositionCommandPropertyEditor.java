package ua.com.itinterview.web.resource.propertyeditor;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.service.PositionService;
import ua.com.itinterview.web.resource.InterviewResource;

import java.beans.PropertyEditorSupport;

public class PositionCommandPropertyEditor extends PropertyEditorSupport {


    public PositionCommandPropertyEditor(PositionService positionService) {
        this.positionService = positionService;
    }

    private PositionService positionService;

    @Override
    public void setAsText(String text) {
        setValue(positionService.getPositionById(Integer.valueOf(text)));
    }
}
