package ua.com.itinterview.entity;

import ua.com.itinterview.web.command.TechnologyCommand;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "technologies")
@SequenceGenerator(name = "sequence", sequenceName = "technologies_id", allocationSize = 1)
public class TechnologyEntity extends EntityWithId {
    private String technologyName;

    public TechnologyEntity() {

    }

    public TechnologyEntity(TechnologyCommand technologyCommand) {
        technologyName = technologyCommand.getName();
    }

    public String getTechnologyName() {
	return technologyName;
    }

    public void setTechnologyName(String technologyName) {
	this.technologyName = technologyName;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ ((technologyName == null) ? 0 : technologyName.hashCode());
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
	TechnologyEntity other = (TechnologyEntity) obj;
	if (technologyName == null) {
	    if (other.technologyName != null)
		return false;
	} else if (!technologyName.equals(other.technologyName))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "TechnologyEntity [technologyName=" + technologyName + "]";
    }

}
