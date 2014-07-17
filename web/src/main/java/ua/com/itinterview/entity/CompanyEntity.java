package ua.com.itinterview.entity;

import ua.com.itinterview.web.command.CompanyCommand;

import javax.persistence.*;

@Entity
@Table(name = "company")
@SequenceGenerator(name = "sequence", sequenceName = "company_id", allocationSize = 1)
public class CompanyEntity extends EntityWithId
{

    public enum CompanyType
    {
        lt80,
        gt80lt200,
        gt200lt800,
        gt800
    }

    private String companyName;
    private String companyPhone;
    private String companyLogoUrl;
    private String companyAddress;
    private String companyWebPage;
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    public CompanyEntity()
    {

    }

    public CompanyEntity(CompanyCommand companyCommand)
    {
        companyName = companyCommand.getName();
        companyPhone = companyCommand.getPhone();
        companyLogoUrl = companyCommand.getLogoURL();
        companyAddress = companyCommand.getAddress();
        companyWebPage = companyCommand.getWebPage();
        companyType = companyCommand.getType();
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCompanyPhone()
    {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone)
    {
        this.companyPhone = companyPhone;
    }

    public String getCompanyLogoUrl()
    {
        return companyLogoUrl;
    }

    public void setCompanyLogoUrl(String companyLogoUrl)
    {
        this.companyLogoUrl = companyLogoUrl;
    }

    public String getCompanyAddress()
    {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress)
    {
        this.companyAddress = companyAddress;
    }

    public String getCompanyWebPage()
    {
        return companyWebPage;
    }

    public void setCompanyWebPage(String companyWebPage)
    {
        this.companyWebPage = companyWebPage;
    }

    @Enumerated(EnumType.STRING)
    public CompanyType getType()
    {
        return companyType;
    }

    public void setType(CompanyType type)
    {
        this.companyType = type;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((companyAddress == null) ? 0 : companyAddress.hashCode());
        result = prime * result
                + ((companyLogoUrl == null) ? 0 : companyLogoUrl.hashCode());
        result = prime * result
                + ((companyName == null) ? 0 : companyName.hashCode());
        result = prime * result
                + ((companyPhone == null) ? 0 : companyPhone.hashCode());
        result = prime * result
                + ((companyType == null) ? 0 : companyType.hashCode());
        result = prime * result
                + ((companyWebPage == null) ? 0 : companyWebPage.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!super.equals(obj))
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        CompanyEntity other = (CompanyEntity) obj;
        if (companyAddress == null)
        {
            if (other.companyAddress != null)
            {
                return false;
            }
        }
        else if (!companyAddress.equals(other.companyAddress))
        {
            return false;
        }
        if (companyLogoUrl == null)
        {
            if (other.companyLogoUrl != null)
            {
                return false;
            }
        }
        else if (!companyLogoUrl.equals(other.companyLogoUrl))
        {
            return false;
        }
        if (companyName == null)
        {
            if (other.companyName != null)
            {
                return false;
            }
        }
        else if (!companyName.equals(other.companyName))
        {
            return false;
        }
        if (companyPhone == null)
        {
            if (other.companyPhone != null)
            {
                return false;
            }
        }
        else if (!companyPhone.equals(other.companyPhone))
        {
            return false;
        }
        if (companyType != other.companyType)
        {
            return false;
        }
        if (companyWebPage == null)
        {
            if (other.companyWebPage != null)
            {
                return false;
            }
        }
        else if (!companyWebPage.equals(other.companyWebPage))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "CompanyEntity{" +
                "companyName='" + companyName + '\'' +
                ", companyPhone='" + companyPhone + '\'' +
                ", companyLogoUrl='" + companyLogoUrl + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyWebPage='" + companyWebPage + '\'' +
                ", companyType=" + companyType +
                '}';
    }
}