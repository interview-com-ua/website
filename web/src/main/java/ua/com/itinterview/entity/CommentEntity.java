package ua.com.itinterview.entity;

import ua.com.itinterview.web.command.CommentCommand;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@SequenceGenerator(name = "sequence", sequenceName = "comments_id", allocationSize = 1)
public class CommentEntity extends EntityWithId
{

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity questionEntity;
    private int rate = 0;
    private String authorName;
    private String email;
    @Column(columnDefinition = "clob")
    private String commentText;
    private String userpicUrl;

    public CommentEntity(CommentCommand commandMock)
    {
        authorName = commandMock.getAuthorName();
        email = commandMock.getEmail();
        commentText = commandMock.getCommentText();
        userpicUrl = commandMock.getUserpicUrl();
        rate = commandMock.getRate();

    }

    public CommentEntity()
    {

    }

    public QuestionEntity getQuestionEntity()
    {
        return questionEntity;
    }

    public void setQuestionEntity(QuestionEntity questionEntity)
    {
        this.questionEntity = questionEntity;
    }

    public int getRate()
    {
        return rate;
    }

    public void setRate(int rate)
    {
        this.rate = rate;
    }

    public String getAuthorName()
    {
        return authorName;
    }

    public void setAuthorName(String authorName)
    {
        this.authorName = authorName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getCommentText()
    {
        return commentText;
    }

    public void setCommentText(String commentText)
    {
        this.commentText = commentText;
    }

    public String getUserpicUrl()
    {
        return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl)
    {
        this.userpicUrl = userpicUrl;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((authorName == null) ? 0 : authorName.hashCode());
        result = prime * result
                + ((commentText == null) ? 0 : commentText.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result
                + ((questionEntity == null) ? 0 : questionEntity.hashCode());
        result = prime * result + rate;
        result = prime * result
                + ((userpicUrl == null) ? 0 : userpicUrl.hashCode());
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
        CommentEntity other = (CommentEntity) obj;
        if (authorName == null)
        {
            if (other.authorName != null)
            {
                return false;
            }
        }
        else if (!authorName.equals(other.authorName))
        {
            return false;
        }
        if (commentText == null)
        {
            if (other.commentText != null)
            {
                return false;
            }
        }
        else if (!commentText.equals(other.commentText))
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
        if (questionEntity == null)
        {
            if (other.questionEntity != null)
            {
                return false;
            }
        }
        else if (!questionEntity.equals(other.questionEntity))
        {
            return false;
        }
        if (rate != other.rate)
        {
            return false;
        }
        if (userpicUrl == null)
        {
            if (other.userpicUrl != null)
            {
                return false;
            }
        }
        else if (!userpicUrl.equals(other.userpicUrl))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "CommentEntity [questionEntity=" + questionEntity + ", rate="
                + rate + ", authorName=" + authorName + ", email=" + email
                + ", commentText=" + commentText + ", userpicUrl=" + userpicUrl
                + "]";
    }

}
