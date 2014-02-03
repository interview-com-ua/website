package ua.com.itinterview.web.integration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.itinterview.entity.UserEntity;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-web-context.xml"})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
       })
public abstract class BaseWebIntegrationTest implements BaseWebIntegrationTestConstants{

    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @After
    public void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    protected MockHttpServletRequestBuilder registerUser(){
        return registerUser(NAME, EMAIL, PASSWORD, PASSWORD);
    }

    protected MockHttpServletRequestBuilder registerUser(String name, String email, String password, String confirmPassword) {
        return post("/register")
                .param("name", name)
                .param("email", email)
                .param("password", password)
                .param("confirmPassword", confirmPassword);
    }

    protected MockHttpServletRequestBuilder loginUser(){
        return loginUser(EMAIL, PASSWORD);
    }

    protected MockHttpServletRequestBuilder loginUser(String email, String password) {
        return post("/j_spring_security_check")
                .param("j_username", email)
                .param("j_password", password)
                .param("_spring_security_remember_me", "on");
    }

    protected MockHttpServletRequestBuilder logout(MockHttpSession session) throws Exception {
        return (post("/j_spring_security_logout").session(session));
    }

    protected UserEntity createTestUser() throws Exception {
        return createTestUser(NAME, EMAIL, PASSWORD);
    }

    protected UserEntity createTestUser(String name, String email, String password) throws Exception {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    protected MockHttpSession getHttpSession(ResultActions actions) {
        return (MockHttpSession) actions.andReturn().getRequest().getSession();
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
