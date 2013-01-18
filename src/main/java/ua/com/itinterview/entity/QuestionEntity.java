package ua.com.itinterview.entity;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "interview_question")
@SequenceGenerator(name = "sequence", sequenceName = "interview_question_id", allocationSize = 1)
public class QuestionEntity extends EntityWithId {
    private Integer interviewId;
    private String question;
    private Integer userId;

    public Integer getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    public String getQuestion() {
        return question;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        QuestionEntity that = (QuestionEntity) o;

        if (!interviewId.equals(that.interviewId)) return false;
        if (!question.equals(that.question)) return false;
        if (!userId.equals(that.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + interviewId.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + question.hashCode();
        return result;
    }

}
