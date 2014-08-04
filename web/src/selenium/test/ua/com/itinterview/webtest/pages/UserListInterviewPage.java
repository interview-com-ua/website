package ua.com.itinterview.webtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by kuznetsov.danil on 7/30/14.
 */
public class UserListInterviewPage {
    public ProxyWebElement btnAddInterview;
    public ProxyWebElement emptyListLabel;
    public ProxyWebElement listInterviewByUser;

    private WebDriver driver;

    public UserListInterviewPage(WebDriver driver) {
        btnAddInterview= new ProxyWebElement(driver,"btnAddInterview");
        emptyListLabel = new ProxyWebElement(driver,"labelEmptyListInterviewByUser");
        listInterviewByUser = new ProxyWebElement(driver,"listInterviewByUser");

        this.driver=driver;
    }

    public ProxyWebElement  getLinkCompanyByIndexItem(int index){
        return new ProxyWebElement(driver,By.xpath("//*[@id=\"itemInterviewList"+String.valueOf(index)+"\"]//*[@class=\"interview_company_name\"]/a" ));
    }

    public ProxyWebElement  getLinkCityByIndexItemList(int index){
        return new ProxyWebElement(driver,By.xpath("//*[@id=\"itemInterviewList"+String.valueOf(index)+"\"]//*[@class=\"interview_company_city\"]/a" ));
    }

    public ProxyWebElement getLinkPositionByIndexItemList(int index){
        return new ProxyWebElement(driver,By.xpath("//*[@id=\"itemInterviewList"+String.valueOf(index)+"\"]//*[@class=\"interview_company_vacancy\"]/a"));
    }

    public int getCountListInterview(){
      return   driver.findElements(By.id("listInterviewByUser")).size();
    }
}
