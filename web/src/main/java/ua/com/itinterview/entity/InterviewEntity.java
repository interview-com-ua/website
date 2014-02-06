package ua.com.itinterview.entity;

import org.hibernate.annotations.Formula;
import ua.com.itinterview.web.command.InterviewCommand;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "interview")
@SequenceGenerator(name = "sequence", sequenceName = "interview_id", allocationSize = 1)
public class InterviewEntity extends EntityWithId {

    @ManyToOne
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

    @Formula("(select count(*) from interview_question q where q.id = id)")
    private int questionCount;

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

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    @PrePersist
    public void prePersist() {
        created = new Date();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        InterviewEntity that = (InterviewEntity) o;

        if (questionCount != that.questionCount) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (feedback != null ? !feedback.equals(that.feedback) : that.feedback != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (technology != null ? !technology.equals(that.technology) : that.technology != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (technology != null ? technology.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + questionCount;
        return result;
    }

    @Override
    public String toString() {
        return "InterviewEntity{" +
                "user=" + user +
                ", company=" + company +
                ", technology=" + technology +
                ", position=" + position +
                ", city=" + city +
                ", feedback='" + feedback + '\'' +
                ", created=" + created +
                ", questionCount=" + questionCount +
                '}';
    }
}
