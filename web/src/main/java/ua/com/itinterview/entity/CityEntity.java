package ua.com.itinterview.entity;

import ua.com.itinterview.web.command.CityCommand;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "city")
@SequenceGenerator(name = "sequence", sequenceName = "city_id", allocationSize = 1)
public class CityEntity extends EntityWithId {

    @Column(length = 100)
    private String cityName;

    public CityEntity() {

    }

    public CityEntity(CityCommand cityCommand) {
        cityName = cityCommand.getCityName();
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
	CityEntity other = (CityEntity) obj;
	if (cityName == null) {
	    if (other.cityName != null)
		return false;
	} else if (!cityName.equals(other.cityName))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "CityEntity [cityName=" + cityName + "]";
    }

}
