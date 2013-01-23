package ua.com.itinterview.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "feedbacks")
@SequenceGenerator(name = "sequence", sequenceName = "feedbacks_id", allocationSize = 1)
public class FeedbackEntity extends EntityWithId {
    // text
    // timestamp
    private String feedbackText;
    private Timestamp createTime;
    private boolean checked;

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
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
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FeedbackEntity other = (FeedbackEntity) obj;
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
	return "FeedbackEntity [feedbackText=" + feedbackText + ", createTime="
		+ createTime + "]";
    }

    public String getFeedbackText() {
	return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
	this.feedbackText = feedbackText;
    }

    public Timestamp getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
	this.createTime = createTime;
    }

    public boolean isChecked() {
	return checked;
    }

    public void setChecked(boolean checked) {
	this.checked = checked;
    }

}
