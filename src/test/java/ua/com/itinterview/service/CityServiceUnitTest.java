package ua.com.itinterview.service;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ua.com.itinterview.dao.CityDao;
import ua.com.itinterview.entity.CityEntity;
import ua.com.itinterview.web.command.CityCommand;

public class CityServiceUnitTest {

    private static final String CITY_NAME = "Kyiv";
    private static final int CITY_ID = 13;
    private CityDao cityDaoMock;
    private CityService cityService;

    @Before
    public void setUpMocks() {
	cityDaoMock = createMock(CityDao.class);
	cityService = new CityService();
	cityService.cityDao = cityDaoMock;

    }

    private void replayAllMocks() {
	replay(cityDaoMock);
    }

    @Test
    public void testConvertFromEntityToCommand() {
	replayAllMocks();
	CityCommand expectedCommand = createCityCommand();
	CityEntity cityEntity = createCityEntity(CITY_ID, CITY_NAME);
	assertEquals(expectedCommand, new CityCommand(cityEntity));
    }

    @Test
    public void testGetAllCities() {
	CityEntity kiev = createCityEntity(2, "Kiev");
	CityEntity moscow = createCityEntity(3, "Moskow");

	expect(cityDaoMock.getAll()).andReturn(Arrays.asList(kiev, moscow));

	replayAllMocks();
	assertEquals(
		Arrays.asList(new CityCommand(kiev), new CityCommand(moscow)),
		cityService.getCities());

    }

    private CityCommand createCityCommand() {
	CityCommand city = new CityCommand();
	city.setCityName(CITY_NAME);
	city.setId(CITY_ID);
	return city;
    }

    private CityEntity createCityEntity(int id, String cityName) {
	CityEntity entity = new CityEntity();
	entity.setCityName(cityName);
	entity.setId(id);
	return entity;
    }

    @After
    public void verifyAllMocks() {
	verify(cityDaoMock);
    }

}
