package ua.com.itinterview.entity;

import ua.com.itinterview.web.command.FeedbackCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "feedbacks")
@SequenceGenerator(name = "sequence", sequenceName = "feedbacks_id", allocationSize = 1)
public class FeedbackEntity extends EntityWithId
{

    @Column(columnDefinition = "clob")
    private String feedbackText;
    private Date createTime;
    private boolean checked;
    private String email;

    public FeedbackEntity(FeedbackCommand commandMock)
    {
        feedbackText = commandMock.getFeedbackText();
        createTime = commandMock.getCreateTime();
        checked = commandMock.isChecked();
        email = commandMock.getEmail();
    }

    public FeedbackEntity()
    {

    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFeedbackText()
    {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText)
    {
        this.feedbackText = feedbackText;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (checked ? 1231 : 1237);
        result = prime * result
                + ((createTime == null) ? 0 : createTime.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result
                + ((feedbackText == null) ? 0 : feedbackText.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!super.equals(obj))
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        FeedbackEntity other = (FeedbackEntity) obj;
        if (checked != other.checked)
        {
            return false;
        }
        if (createTime == null)
        {
            if (other.createTime != null)
            {
                return false;
            }
        }
        else if (!createTime.equals(other.createTime))
        {
            return false;
        }
        if (email == null)
        {
            if (other.email != null)
            {
                return false;
            }
        }
        else if (!email.equals(other.email))
        {
            return false;
        }
        if (feedbackText == null)
        {
            if (other.feedbackText != null)
            {
                return false;
            }
        }
        else if (!feedbackText.equals(other.feedbackText))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "FeedbackEntity [feedbackText=" + feedbackText + ", createTime="
                + createTime + ", checked=" + checked + ", email=" + email
                + "]";
    }

}
