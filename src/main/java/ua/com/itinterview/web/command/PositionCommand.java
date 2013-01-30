package ua.com.itinterview.web.command;

import ua.com.itinterview.entity.PositionEntity;

public class PositionCommand {

    private String group;
    private String name;

    public PositionCommand() {

    }

    public PositionCommand(PositionEntity entity) {
	group = entity.getPositionGroup();
	name = entity.getPositionName();
    }

    public String getGroup() {
	return group;
    }

    public void setGroup(String group) {
	this.group = group;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((group == null) ? 0 : group.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	PositionCommand other = (PositionCommand) obj;
	if (group == null) {
	    if (other.group != null)
		return false;
	} else if (!group.equals(other.group))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "PositionCommand [group=" + group + ", name=" + name + "]";
    }

}
