package ua.com.itinterview.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "interview")
@SequenceGenerator(name = "sequence", sequenceName = "interview_id", allocationSize = 1)
public class InterviewEntity extends EntityWithId {

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity user;
    private String feedback;
    private Date created;

    public InterviewEntity() {

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
	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result
		+ ((feedback == null) ? 0 : feedback.hashCode());
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
	if (user == null) {
	    if (other.user != null)
		return false;
	} else if (!user.equals(other.user))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "InterviewEntity [user=" + user + ", feedback=" + feedback
		+ ", created=" + created + "]";
    }

}
