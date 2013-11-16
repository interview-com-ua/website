package ua.com.itinterview.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.entity.CityEntity;
import ua.com.itinterview.entity.CommentEntity;
import ua.com.itinterview.entity.CompanyEntity;
import ua.com.itinterview.entity.FeedbackEntity;
import ua.com.itinterview.entity.InterviewEntity;
import ua.com.itinterview.entity.PositionEntity;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.TechnologyEntity;
import ua.com.itinterview.entity.UserEntity;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-context.xml" })
public abstract class BaseDaoIntegrationTest extends
	AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    public final static String TEST_USER_EMAIL = "test.user@gmail.com";

    protected UserEntity testUser;

    private final List<Class<?>> entities;

    private final static String CLEANUP_TABLE_SQL = "delete from %s";

    public BaseDaoIntegrationTest() {
	entities = new ArrayList<Class<?>>();
	entities.add(InterviewEntity.class);
	entities.add(CompanyEntity.class);
	entities.add(CommentEntity.class);
	entities.add(QuestionEntity.class);
	entities.add(PositionEntity.class);
	entities.add(TechnologyEntity.class);
	entities.add(FeedbackEntity.class);
	entities.add(CityEntity.class);
	entities.add(UserEntity.class);
    }

    @Before
    public void setUp() {
	cleanUpDb();
	testUser = createTestUser();
    }

    private UserEntity createTestUser() {
	UserEntity user = new UserEntity();
	user.setEmail(TEST_USER_EMAIL);
	user.setPassword("password");
	return userDao.save(user);
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
    public void afterTest() {
	cleanUpDb();
    }

    @Transactional
    public void cleanUpDb() {
	try {
	    for (Class<?> ent : entities) {
		cleanUpEntity(ent);
	    }
	} catch (Exception ex) {
	    System.err.println("Error during cleanUpDb");
	    ex.printStackTrace();
	    throw new RuntimeException(ex);
	}

    }

    public void cleanUpEntity(Class<?> ent) {
	Session session = sessionFactory.getCurrentSession();
	String sqlQueryCleanupTable = String.format(CLEANUP_TABLE_SQL,
		ent.getSimpleName());
	Query query = session.createQuery(sqlQueryCleanupTable);
	query.executeUpdate();
    }

}