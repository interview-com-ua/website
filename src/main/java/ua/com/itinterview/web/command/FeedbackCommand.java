package ua.com.itinterview.web.command;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import ua.com.itinterview.entity.FeedbackEntity;

public class FeedbackCommand {
    @NotNull
    @NotBlank
    private String feedbackText;
    @NotNull
    @NotBlank
    @Email
    private String email;
    private Date createTime = new Date();
    private boolean checked = false;

    public FeedbackCommand(FeedbackEntity entity) {
	feedbackText = entity.getFeedbackText();
	createTime = entity.getCreateTime();
	checked = entity.isChecked();
	email = entity.getEmail();
    }

    public FeedbackCommand() {
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getFeedbackText() {
	return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
	this.feedbackText = feedbackText;
    }

    public Date getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    public boolean isChecked() {
	return checked;
    }

    public void setChecked(boolean checked) {
	this.checked = checked;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (checked ? 1231 : 1237);
	result = prime * result
		+ ((createTime == null) ? 0 : createTime.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result
		+ ((feedbackText == null) ? 0 : feedbackText.hashCode());
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
	FeedbackCommand other = (FeedbackCommand) obj;
	if (checked != other.checked)
	    return false;
	if (createTime == null) {
	    if (other.createTime != null)
		return false;
	} else if (!createTime.equals(other.createTime))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (feedbackText == null) {
	    if (other.feedbackText != null)
		return false;
	} else if (!feedbackText.equals(other.feedbackText))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "FeedbackCommand [feedbackText=" + feedbackText
		+ ", createTime=" + createTime + ", checked=" + checked
		+ ", email=" + email + "]";
    }

}