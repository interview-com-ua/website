package ua.com.itinterview.web.command;

import ua.com.itinterview.entity.CityEntity;

public class CityCommand {

    private int id;
    private String cityName;

    public CityCommand() {
    }

    public CityCommand(CityEntity cityEntity) {
	cityName = cityEntity.getCityName();
	id = cityEntity.getId();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getCityName() {
	return cityName;
    }

    public void setCityName(String cityName) {
	this.cityName = cityName;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((cityName == null) ? 0 : cityName.hashCode());
	result = prime * result + id;
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
	CityCommand other = (CityCommand) obj;
	if (cityName == null) {
	    if (other.cityName != null)
		return false;
	} else if (!cityName.equals(other.cityName))
	    return false;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "CityCommand [id=" + id + ", cityName=" + cityName + "]";
    }

}
