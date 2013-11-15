package ua.com.itinterview.web.command;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import ua.com.itinterview.entity.CommentEntity;

public class CommentCommand {

    private int rate = 0;
    @NotEmpty(message = "Author name cannot be empty.")
    private String authorName;
    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Please enter valid email address.")
    private String email;
    @NotEmpty(message = "Comment text cannot be empty.")
    private String commentText;
    private String userpicUrl;

    public CommentCommand(CommentEntity entity) {
	authorName = entity.getAuthorName();
	email = entity.getEmail();
	commentText = entity.getCommentText();
	userpicUrl = entity.getUserpicUrl();
	rate = entity.getRate();
    }

    public CommentCommand() {

    }

    public int getRate() {
	return rate;
    }

    public void setRate(int rate) {
	this.rate = rate;
    }

    public String getAuthorName() {
	return authorName;
    }

    public void setAuthorName(String authorName) {
	this.authorName = authorName;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getCommentText() {
	return commentText;
    }

    public void setCommentText(String commentText) {
	this.commentText = commentText;
    }

    public String getUserpicUrl() {
	return userpicUrl;
    }

    public void setUserpicUrl(String userpicUrl) {
	this.userpicUrl = userpicUrl;
    }

    @Override
    public String toString() {
	return "CommentCommand [rate=" + rate + ", authorName=" + authorName
		+ ", email=" + email + ", commentText=" + commentText
		+ ", userpicUrl=" + userpicUrl + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((authorName == null) ? 0 : authorName.hashCode());
	result = prime * result
		+ ((commentText == null) ? 0 : commentText.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + rate;
	result = prime * result
		+ ((userpicUrl == null) ? 0 : userpicUrl.hashCode());
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
	CommentCommand other = (CommentCommand) obj;
	if (authorName == null) {
	    if (other.authorName != null)
		return false;
	} else if (!authorName.equals(other.authorName))
	    return false;
	if (commentText == null) {
	    if (other.commentText != null)
		return false;
	} else if (!commentText.equals(other.commentText))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (rate != other.rate)
	    return false;
	if (userpicUrl == null) {
	    if (other.userpicUrl != null)
		return false;
	} else if (!userpicUrl.equals(other.userpicUrl))
	    return false;
	return true;
    }

}
