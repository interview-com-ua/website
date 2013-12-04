package ua.com.itinterview.web.resource.propertyeditor;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.service.CompanyService;

import java.beans.PropertyEditorSupport;

public class CompanyCommandPropertyEditor extends PropertyEditorSupport {


    public CompanyCommandPropertyEditor(CompanyService companyService) {
        this.companyService = companyService;
    }

    private CompanyService companyService;

    @Override
    public void setAsText(String text) {
        setValue(companyService.getCompanyById(Integer.valueOf(text)));
    }
}
