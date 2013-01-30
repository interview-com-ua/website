package ua.com.itinterview.web.command;

import ua.com.itinterview.entity.CompanyEntity;

public class CompanyCommand {

    private String name;
    private String phone;
    private String logoURL;
    private String address;
    private String webPage;

    public CompanyCommand() {

    }

    public CompanyCommand(CompanyEntity entity) {	
	name = entity.getCompanyName();
	phone = entity.getCompanyPhone();
	logoURL = entity.getCompanyLogoUrl();
	address = entity.getCompanyAddress();
	webPage = entity.getCompanyWebPage();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getLogoURL() {
	return logoURL;
    }

    public void setLogoURL(String logoURL) {
	this.logoURL = logoURL;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getWebPage() {
	return webPage;
    }

    public void setWebPage(String webPage) {
	this.webPage = webPage;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((logoURL == null) ? 0 : logoURL.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	result = prime * result + ((webPage == null) ? 0 : webPage.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CompanyCommand other = (CompanyCommand) obj;
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (logoURL == null) {
	    if (other.logoURL != null)
		return false;
	} else if (!logoURL.equals(other.logoURL))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (phone == null) {
	    if (other.phone != null)
		return false;
	} else if (!phone.equals(other.phone))
	    return false;
	if (webPage == null) {
	    if (other.webPage != null)
		return false;
	} else if (!webPage.equals(other.webPage))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "CompanyCommand [name=" + name + ", phone=" + phone
		+ ", logoURL=" + logoURL + ", address=" + address
		+ ", webPage=" + webPage + "]";
    }

}
