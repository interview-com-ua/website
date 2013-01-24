package ua.com.itinterview.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import ua.com.itinterview.web.command.QuestionCommand;

@Entity
@Table(name = "interview_question")
@SequenceGenerator(name = "sequence", sequenceName = "interview_question_id", allocationSize = 1)
public class QuestionEntity extends EntityWithId {

    @ManyToOne
    @JoinColumn(name = "interviewid")
    private InterviewEntity interview;
    private String question; // TODO Rename this field questionText
    private Date created;

    public QuestionEntity() {

    }

    public QuestionEntity(QuestionCommand questionCommand) {
	question = questionCommand.getQuestion();
    }

    public InterviewEntity getInterview() {
	return interview;
    }

    public void setInterview(InterviewEntity interview) {
	this.interview = interview;
    }

    public String getQuestion() {
	return question;
    }

    public void setQuestion(String question) {
	this.question = question;
    }

    public Date getCreate() {
	return created;
    }

    public void setCreate(Date create) {
	this.created = create;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result
		+ ((interview == null) ? 0 : interview.hashCode());
	result = prime * result
		+ ((question == null) ? 0 : question.hashCode());
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
	QuestionEntity other = (QuestionEntity) obj;
	if (created == null) {
	    if (other.created != null)
		return false;
	} else if (!created.equals(other.created))
	    return false;
	if (interview == null) {
	    if (other.interview != null)
		return false;
	} else if (!interview.equals(other.interview))
	    return false;
	if (question == null) {
	    if (other.question != null)
		return false;
	} else if (!question.equals(other.question))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "QuestionEntity [interview=" + interview + ", question="
		+ question + ", created=" + created + "]";
    }

}
