package ua.com.itinterview.web.command;

import ua.com.itinterview.entity.CompanyEntity;

public class CompanyCommand {

    private Integer id;
    private String name;
    private String phone;
    private String logoURL;
    private String address;
    private String webPage;
    private CompanyEntity.CompanyType type;

    public CompanyCommand() {

    }

    public CompanyCommand(CompanyEntity entity) {
        name = entity.getCompanyName();
        phone = entity.getCompanyPhone();
        logoURL = entity.getCompanyLogoUrl();
        address = entity.getCompanyAddress();
        webPage = entity.getCompanyWebPage();
        id = entity.getId();
        type = entity.getType();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public CompanyEntity.CompanyType getType() {
        return type;
    }

    public void setType(CompanyEntity.CompanyType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompanyCommand that = (CompanyCommand) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (logoURL != null ? !logoURL.equals(that.logoURL) : that.logoURL != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (type != that.type) return false;
        if (webPage != null ? !webPage.equals(that.webPage) : that.webPage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (logoURL != null ? logoURL.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (webPage != null ? webPage.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompanyCommand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", logoURL='" + logoURL + '\'' +
                ", address='" + address + '\'' +
                ", webPage='" + webPage + '\'' +
                ", type=" + type +
                '}';
    }
}
