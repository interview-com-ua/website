package ua.com.itinterview.web.command;

import ua.com.itinterview.entity.TechnologyEntity;

public class TechnologyCommand {

    private Integer id;
    private String name;

    public TechnologyCommand() {

    }

    public TechnologyCommand(TechnologyEntity entity) {
	name = entity.getTechnologyName();
	id = entity.getId();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
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
	TechnologyCommand other = (TechnologyCommand) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
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
	return "TechnologyCommand [id=" + id + ", name=" + name + "]";
    }

}
