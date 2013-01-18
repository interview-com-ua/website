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
}
