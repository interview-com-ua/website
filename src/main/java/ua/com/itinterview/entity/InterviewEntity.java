package ua.com.itinterview.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import sun.text.UCompactIntArray;
import ua.com.itinterview.web.command.InterviewCommand;
import ua.com.itinterview.web.command.UserCommand;

@Entity
@Table(name = "interview")
@SequenceGenerator(name = "sequence", sequenceName = "interview_id", allocationSize = 1)
public class InterviewEntity extends EntityWithId {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
    @ManyToOne
    @JoinColumn(name = "technology_id")
    private TechnologyEntity technology;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private PositionEntity position;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;
    private String feedback;
    private Date created;

    public InterviewEntity() {

    }

    public InterviewEntity(InterviewCommand interviewCommand) {
        user = new UserEntity(interviewCommand.getUser());
        feedback = interviewCommand.getFeedback();
        created = interviewCommand.getCreated();
        company = new CompanyEntity(interviewCommand.getCompany());
        technology = new TechnologyEntity(interviewCommand.getTechnology());
        position = new PositionEntity(interviewCommand.getPosition());
        city = new CityEntity(interviewCommand.getCity());
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public TechnologyEntity getTechnology() {
        return technology;
    }

    public void setTechnology(TechnologyEntity technology) {
        this.technology = technology;
    }

    public PositionEntity getPosition() {
        return position;
    }

    public void setPosition(PositionEntity position) {
        this.position = position;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result
                + ((feedback == null) ? 0 : feedback.hashCode());
        result = prime * result
                + ((position == null) ? 0 : position.hashCode());
        result = prime * result
                + ((technology == null) ? 0 : technology.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        InterviewEntity other = (InterviewEntity) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (!company.equals(other.company))
            return false;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (feedback == null) {
            if (other.feedback != null)
                return false;
        } else if (!feedback.equals(other.feedback))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (technology == null) {
            if (other.technology != null)
                return false;
        } else if (!technology.equals(other.technology))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "InterviewEntity [user=" + user + ", company=" + company
                + ", technology=" + technology + ", position=" + position
                + ", city=" + city + ", feedback=" + feedback + ", created="
                + created + "]";
    }

}
