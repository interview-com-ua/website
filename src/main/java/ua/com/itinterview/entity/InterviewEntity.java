package ua.com.itinterview.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "interview")
@SequenceGenerator(name = "sequence", sequenceName = "interview_id", allocationSize = 1)
public class InterviewEntity extends EntityWithId {

    private int userId;
    private String feedback;
    private String created;

    public InterviewEntity(){

    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
