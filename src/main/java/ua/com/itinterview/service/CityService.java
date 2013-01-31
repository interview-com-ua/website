package ua.com.itinterview.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ua.com.itinterview.dao.CityDao;
import ua.com.itinterview.entity.CityEntity;
import ua.com.itinterview.web.command.CityCommand;

public class CityService {

    @Autowired
    CityDao cityDao;

    public List<CityCommand> getCities() {
	List<CityEntity> cities = cityDao.getAll();
	List<CityCommand> result = new ArrayList<CityCommand>(cities.size());
	for (CityEntity cityEntity : cities) {
	    result.add(new CityCommand(cityEntity));
	}
	return result;
    }

}
