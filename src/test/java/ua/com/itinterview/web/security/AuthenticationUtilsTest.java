package ua.com.itinterview.web.security;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:context.xml"})
public class AuthenticationUtilsTest {

    @Autowired
    AuthenticationUtils authenticationUtils;

    @Test
    public void testGetMD5Hash() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String valueToEncode = "value";
        String expectedResult = "2063c1608d6e0baf80249c42e2be5804";
        String actualResult = authenticationUtils.getMD5Hash(valueToEncode);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test()
    public void testGetMD5HashWhenValueEmpty() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String valueToEncode = "";
        String expectedResult = "d41d8cd98f00b204e9800998ecf8427e";
        String actualResult = authenticationUtils.getMD5Hash(valueToEncode);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test(expected = NullPointerException.class)
    public void testGetMD5HashWhenValueNull() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String valueToEncode = null;
        authenticationUtils.getMD5Hash(valueToEncode);
    }
}
