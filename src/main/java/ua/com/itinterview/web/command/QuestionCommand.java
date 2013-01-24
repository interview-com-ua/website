package ua.com.itinterview.web.command;

import ua.com.itinterview.entity.QuestionEntity;

public class QuestionCommand {

    private String question;

    public QuestionCommand() {
    }

    public QuestionCommand(QuestionEntity questionEntity) {
	this.question = questionEntity.getQuestion();
    }

    public String getQuestion() {
	return question;
    }

    public void setQuestion(String question) {
	this.question = question;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((question == null) ? 0 : question.hashCode());
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
	if (question == null) {
	    if (other.question != null)
		return false;
	} else if (!question.equals(other.question))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "QuestionCommand [question=" + question + "]";
    }

}
