package ua.com.itinterview.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "interview")
@SequenceGenerator(name = "sequence", sequenceName = "interview_id", allocationSize = 1)
public class InterviewEntity extends EntityWithId {

    private Integer userId;
    private String feedback;
    private Date created;

    public InterviewEntity() {

    }

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
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
	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result
		+ ((feedback == null) ? 0 : feedback.hashCode());
	result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
	if (userId == null) {
	    if (other.userId != null)
		return false;
	} else if (!userId.equals(other.userId))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "InterviewEntity [userId=" + userId + ", feedback=" + feedback
		+ ", created=" + created + "]";
    }

}
