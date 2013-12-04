package ua.com.itinterview.web.resource.propertyeditor;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.service.CityService;


import java.beans.PropertyEditorSupport;

public class CityCommandPropertyEditor extends PropertyEditorSupport {


    private CityService cityService;

    public CityCommandPropertyEditor(CityService cityService) {
        this.cityService=cityService;
    }

    @Override
    public void setAsText(String text) {
        setValue(cityService.getCityById(Integer.valueOf(text)));
    }
}


