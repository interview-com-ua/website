package ua.com.itinterview.web.command;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;

public class InterviewCommand {

    private Integer id;
    private UserCommand user;
    private CompanyCommand company;
    private TechnologyCommand technology;
    private PositionCommand position;
    private CityCommand city;

    @NotNull
    @NotBlank
    private String feedback;
    private Date created;

    public InterviewCommand() {

    }

    public InterviewCommand(InterviewEntity interviewEntity) {
        id = interviewEntity.getId();
        user = new UserCommand(interviewEntity.getUser());
        feedback = interviewEntity.getFeedback();
        created = interviewEntity.getCreated();
        company = new CompanyCommand(interviewEntity.getCompany());
        technology = new TechnologyCommand(interviewEntity.getTechnology());
        position = new PositionCommand(interviewEntity.getPosition());
        city = new CityCommand(interviewEntity.getCity());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserCommand getUser() {
        return user;
    }

    public void setUser(UserCommand user) {
        this.user = user;
    }

    public CompanyCommand getCompany() {
        return company;
    }

    public void setCompany(CompanyCommand company) {
        this.company = company;
    }

    public TechnologyCommand getTechnology() {
        return technology;
    }

    public void setTechnology(TechnologyCommand technology) {
        this.technology = technology;
    }

    public PositionCommand getPosition() {
        return position;
    }

    public void setPosition(PositionCommand position) {
        this.position = position;
    }

    public CityCommand getCity() {
        return city;
    }

    public void setCity(CityCommand city) {
        this.city = city;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterviewCommand that = (InterviewCommand) o;

        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (feedback != null ? !feedback.equals(that.feedback) : that.feedback != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (technology != null ? !technology.equals(that.technology) : that.technology != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (technology != null ? technology.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InterviewCommand{" +
                "id=" + id +
                ", user=" + user +
                ", company=" + company +
                ", technology=" + technology +
                ", position=" + position +
                ", city=" + city +
                ", feedback='" + feedback + '\'' +
                ", created=" + created +
                '}';
    }
}
