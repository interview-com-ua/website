package ua.com.itinterview.web.integration;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.itinterview.dao.UserDao;
import ua.com.itinterview.entity.UserEntity;
import ua.com.itinterview.web.security.AuthenticationUtils;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-web-context.xml"})
public abstract class BaseWebIntegrationTest extends
        AbstractTransactionalJUnit4SpringContextTests {

    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    UserDao userDao;

    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    protected MockHttpServletRequestBuilder registerUser(String userName,
                                                         String name, String email, String password, String confirmPassword) {
        return registerUser(userName, name, email, password, confirmPassword, UserEntity.Sex.MALE);
    }

    protected MockHttpServletRequestBuilder registerUser(String userName,
                                                         String name, String email, String password, String confirmPassword, UserEntity.Sex sex) {
        return post("/register").param("userName", userName)
                .param("name", name).param("email", email)
                .param("password", password).param("sex", sex.toString())
                .param("confirmPassword", confirmPassword);
    }

    protected MockHttpServletRequestBuilder loginUser(String userName,
                                                      String password) {
        return post("/j_spring_security_check")
                .param("j_username", userName)
                .param("j_password", password)
                .param("_spring_security_remember_me", "on");
    }

    @After
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public MockHttpServletRequestBuilder logout(MockHttpSession session) throws Exception {
        return (post("/j_spring_security_logout").session(session));
    }

    @Rollback
    public UserEntity createUser(String userName, String name, String email, String password) throws Exception {
        return createUser(userName, name, email, password, UserEntity.Sex.FEMALE);
    }

    @Rollback
    public UserEntity createUser(String userName, String name, String email, String password, UserEntity.Sex sex) throws Exception {
        UserEntity userToSave = new UserEntity();
        userToSave.setUserName(userName);
        userToSave.setName(name);
        userToSave.setEmail(email);
        userToSave.setPassword(authenticationUtils.getMD5Hash(password));
        userToSave.setSex(sex);
        return userDao.save(userToSave);
    }

    public static Cookie[] initCookies() {
        return new Cookie[]{
                new Cookie("MY-COOKIE", "DEFAULT_COOKIE_VALUE")
        };
    }

    public static Cookie[] updateCookies(Cookie[] current,
                                         ResultActions result) {
        Map<String, Cookie> currentCookies = new HashMap<String, Cookie>();
        if (current != null) {
            for (Cookie c : current) {
                currentCookies.put(c.getName(), c);
            }
        }

        Cookie[] newCookies = result.andReturn().getResponse().getCookies();
        for (Cookie newCookie : newCookies) {
            if (StringUtils.isBlank(newCookie.getValue())) {
                currentCookies.remove(newCookie.getName());
            } else {
                currentCookies.put(newCookie.getName(), newCookie);
            }
        }
        return currentCookies.values().toArray(new Cookie[currentCookies.size()]);
    }
}
