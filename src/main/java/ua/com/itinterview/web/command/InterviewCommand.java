package ua.com.itinterview.web.command;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.UserEntity;

public class InterviewCommand {

    private Integer id;
    private UserEntity user;
    private Integer companyId;
    private Integer technologyId;
    private Integer positionId;
    private Integer cityId;

    @NotNull
    @NotBlank
    private String feedback;

    private Date created;

    public InterviewCommand() {

    }

    public InterviewCommand(InterviewEntity interviewEntity) {
	id = interviewEntity.getId();
	user = interviewEntity.getUser();
	feedback = interviewEntity.getFeedback();
	created = interviewEntity.getCreated();
	companyId = interviewEntity.getCompany().getId();
	technologyId = interviewEntity.getTechnology().getId();
	positionId = interviewEntity.getPosition().getId();
	cityId = interviewEntity.getCity().getId();
    }

    public Integer getCompanyId() {
	return companyId;
    }

    public void setCompanyId(Integer companyId) {
	this.companyId = companyId;
    }

    public Integer getTechnologyId() {
	return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
	this.technologyId = technologyId;
    }

    public Integer getPositionId() {
	return positionId;
    }

    public void setPositionId(Integer positionId) {
	this.positionId = positionId;
    }

    public Integer getCityId() {
	return cityId;
    }

    public void setCityId(Integer cityId) {
	this.cityId = cityId;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
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
	int result = 1;
	result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
	result = prime * result
		+ ((companyId == null) ? 0 : companyId.hashCode());
	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result
		+ ((feedback == null) ? 0 : feedback.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result
		+ ((positionId == null) ? 0 : positionId.hashCode());
	result = prime * result
		+ ((technologyId == null) ? 0 : technologyId.hashCode());
	result = prime * result + ((user == null) ? 0 : user.hashCode());
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
	InterviewCommand other = (InterviewCommand) obj;
	if (cityId == null) {
	    if (other.cityId != null)
		return false;
	} else if (!cityId.equals(other.cityId))
	    return false;
	if (companyId == null) {
	    if (other.companyId != null)
		return false;
	} else if (!companyId.equals(other.companyId))
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
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (positionId == null) {
	    if (other.positionId != null)
		return false;
	} else if (!positionId.equals(other.positionId))
	    return false;
	if (technologyId == null) {
	    if (other.technologyId != null)
		return false;
	} else if (!technologyId.equals(other.technologyId))
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
	return "InterviewCommand [id=" + id + ", user=" + user + ", companyId="
		+ companyId + ", technologyId=" + technologyId
		+ ", positionId=" + positionId + ", cityId=" + cityId
		+ ", feedback=" + feedback + ", created=" + created + "]";
    }

}
