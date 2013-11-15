package ua.com.itinterview.web.command;

public class QuestionSearchCommand {

    private Integer companyId;
    private Integer technologyId;
    private Integer positionId;

    public Integer getCompanyId() {
	return companyId;
    }

    public void setCompanyId(Integer companyId) {
	this.companyId = companyId;
    }

    public Integer getTechnologyId() {
	return technologyId;
    }

    public void setTechnologyId(Integer technologyId) {
	this.technologyId = technologyId;
    }

    public Integer getPositionId() {
	return positionId;
    }

    public void setPositionId(Integer positionId) {
	this.positionId = positionId;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((companyId == null) ? 0 : companyId.hashCode());
	result = prime * result
		+ ((positionId == null) ? 0 : positionId.hashCode());
	result = prime * result
		+ ((technologyId == null) ? 0 : technologyId.hashCode());
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
	QuestionSearchCommand other = (QuestionSearchCommand) obj;
	if (companyId == null) {
	    if (other.companyId != null)
		return false;
	} else if (!companyId.equals(other.companyId))
	    return false;
	if (positionId == null) {
	    if (other.positionId != null)
		return false;
	} else if (!positionId.equals(other.positionId))
	    return false;
	if (technologyId == null) {
	    if (other.technologyId != null)
		return false;
	} else if (!technologyId.equals(other.technologyId))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "QuestionSearchCommand [companyId=" + companyId
		+ ", technologyId=" + technologyId + ", positionId="
		+ positionId + "]";
    }

}
