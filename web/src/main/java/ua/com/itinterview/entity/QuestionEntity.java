package ua.com.itinterview.entity;

import ua.com.itinterview.web.command.QuestionCommand;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "interview_question")
@SequenceGenerator(name = "sequence", sequenceName = "interview_question_id", allocationSize = 1)
public class QuestionEntity extends EntityWithId {

    @ManyToOne
    @JoinColumn(name = "interviewid")
    private InterviewEntity interview;
    private String title;
    @Column(columnDefinition = "clob")
    private String question;
    @Column(columnDefinition = "clob")
    private String answer;
    @Temporal(TemporalType.DATE)
    private Date created;

    public QuestionEntity() {

    }

    public QuestionEntity(QuestionCommand questionCommand) {
	title = questionCommand.getTitle();
	question = questionCommand.getQuestion();
	answer = questionCommand.getAnswer();
    }

    public InterviewEntity getInterview() {
	return interview;
    }

    public void setInterview(InterviewEntity interview) {
	this.interview = interview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
	return question;
    }

    public void setQuestion(String question) {
	this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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
	result = prime * result + ((answer == null) ? 0 : answer.hashCode());
	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result
		+ ((interview == null) ? 0 : interview.hashCode());
	result = prime * result
		+ ((question == null) ? 0 : question.hashCode());
	result = prime * result + ((title == null) ? 0 : title.hashCode());
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
	if (answer == null) {
	    if (other.answer != null)
		return false;
	} else if (!answer.equals(other.answer))
	    return false;
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
	if (title == null) {
	    if (other.title != null)
		return false;
	} else if (!title.equals(other.title))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "QuestionEntity [interview=" + interview + ", title=" + title
		+ ", question=" + question + ", answer=" + answer
		+ ", created=" + created + "]";
    }
}
