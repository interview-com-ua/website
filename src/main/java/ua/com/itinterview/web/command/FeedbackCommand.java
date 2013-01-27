package ua.com.itinterview.web.command;

import java.util.Date;

import ua.com.itinterview.entity.FeedbackEntity;

public class FeedbackCommand {

    private String feedbackText;
    private Date createTime;
    private boolean checked;

    public FeedbackCommand(FeedbackEntity entity) {
	feedbackText = entity.getFeedbackText();
	createTime = entity.getCreateTime();
	checked = entity.isChecked();
    }

    public FeedbackCommand() {

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
		+ ", createTime=" + createTime + ", checked=" + checked + "]";
    }

}