package ua.com.itinterview.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.itinterview.entity.PositionEntity;
import ua.com.itinterview.entity.TechnologyEntity;
import ua.com.itinterview.entity.UserEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:context.xml" })
public abstract class BaseDaoIntegrationTest extends
	AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionFactory sessionFactory;

    private final List<Class<?>> entities;

    private final static String CLEANUP_TABLE_SQL = "delete %s";

    public BaseDaoIntegrationTest() {
	entities = new ArrayList<Class<?>>();
	entities.add(UserEntity.class);
	entities.add(PositionEntity.class);
	entities.add(TechnologyEntity.class);
    }

    @Before
    public void setUp() throws IOException {
	try {
	    cleanUpDb();
	} catch (RuntimeException ex) {
	    // TODO create smart structure of insert before, clean up after
	}
	final String sql = readDbUpdateScriptFromFile(new File(
		"sql/itinterview_ddl_schema.sql"));
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	jdbcTemplate.execute(new StatementCallback<Object>() {
	    public Object doInStatement(Statement stmt) throws SQLException,
		    DataAccessException {
		String[] scripts = sql.split(";");
		for (String script : scripts) {
		    try {
			stmt.execute(script);
		    } catch (Exception e) {
			// TODO create auto ddl script
		    }
		}
		return null;
	    }
	});
    }

    public String readDbUpdateScriptFromFile(final File file)
	    throws IOException {
	StringBuffer buffer = new StringBuffer();
	FileInputStream fis = new FileInputStream(file);
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	String line = "";
	while ((line = br.readLine()) != null) {
	    buffer.append(line);
	}
	br.close();
	return buffer.toString();
    }

    @After
    @Transactional
    public void cleanUpDb() {
	try {
	    Session session = sessionFactory.getCurrentSession();
	    for (Class<?> ent : entities) {
		session.createQuery(
			String.format(CLEANUP_TABLE_SQL, ent.getSimpleName()))
			.executeUpdate();
	    }
	} catch (Exception ex) {
	    throw new RuntimeException(ex);
	}
    }
}
