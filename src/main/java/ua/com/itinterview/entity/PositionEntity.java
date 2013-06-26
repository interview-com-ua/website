package ua.com.itinterview.entity;

import ua.com.itinterview.web.command.PositionCommand;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "positions")
@SequenceGenerator(name = "sequence", sequenceName = "positions_id", allocationSize = 1)
public class PositionEntity extends EntityWithId {
    private String positionGroup;
    private String positionName;

    public PositionEntity() {

    }

    public PositionEntity(PositionCommand positionCommand) {
        positionGroup = positionCommand.getGroup();
        positionName = positionCommand.getName();
    }

    public String getPositionGroup() {
	return positionGroup;
    }

    public void setPositionGroup(String positionGroup) {
	this.positionGroup = positionGroup;
    }

    public String getPositionName() {
	return positionName;
    }

    public void setPositionName(String positionName) {
	this.positionName = positionName;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ ((positionGroup == null) ? 0 : positionGroup.hashCode());
	result = prime * result
		+ ((positionName == null) ? 0 : positionName.hashCode());
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
	PositionEntity other = (PositionEntity) obj;
	if (positionGroup == null) {
	    if (other.positionGroup != null)
		return false;
	} else if (!positionGroup.equals(other.positionGroup))
	    return false;
	if (positionName == null) {
	    if (other.positionName != null)
		return false;
	} else if (!positionName.equals(other.positionName))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "PositionEntity [positionGroup=" + positionGroup
		+ ", positionName=" + positionName + "]";
    }

}
