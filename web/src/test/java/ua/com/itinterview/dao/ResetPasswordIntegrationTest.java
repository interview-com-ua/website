package ua.com.itinterview.dao;

/**
 * Created by Bohdan on 04/08/2014.
 */
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.entity.ResetPasswordEntity;

import static org.junit.Assert.assertEquals;

public class ResetPasswordIntegrationTest extends BaseDaoIntegrationTest {

    @Autowired
    private ResetPasswordDao resetPasswordDao;

    @Test
    public void shouldSaveResetPassword() {
        ResetPasswordEntity entity = createEntity();
        ResetPasswordEntity savedEntity = resetPasswordDao.save(entity);
        assertEquals(entity, savedEntity);
    }

    private ResetPasswordEntity createEntity() {
        ResetPasswordEntity resetPassword = new ResetPasswordEntity();
        resetPassword.setHash("111111");
        resetPassword.setEmail("email@email.com");
        return resetPassword;
    }
}
