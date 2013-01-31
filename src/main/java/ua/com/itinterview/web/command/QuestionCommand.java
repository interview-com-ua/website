package ua.com.itinterview.web.command;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import ua.com.itinterview.entity.QuestionEntity;

public class QuestionCommand {

    private int id;
    @NotEmpty
    @Length(min = 0, max = 100)
    private String title;
    @NotEmpty
    private String question;
    private String answer;

    public QuestionCommand() {
    }

    public QuestionCommand(QuestionEntity questionEntity) {
	id = questionEntity.getId();
	title = questionEntity.getTitle();
	question = questionEntity.getQuestion();
	answer = questionEntity.getAnswer();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((answer == null) ? 0 : answer.hashCode());
	result = prime * result + id;
	result = prime * result
		+ ((question == null) ? 0 : question.hashCode());
	result = prime * result + ((title == null) ? 0 : title.hashCode());
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
	QuestionCommand other = (QuestionCommand) obj;
	if (answer == null) {
	    if (other.answer != null)
		return false;
	} else if (!answer.equals(other.answer))
	    return false;
	if (id != other.id)
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
	return "QuestionCommand [id=" + id + ", title=" + title + ", question="
		+ question + ", answer=" + answer + "]";
    }
}
