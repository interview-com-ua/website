package ua.com.itinterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.itinterview.dao.ResetPasswordDao;
import ua.com.itinterview.entity.ResetPasswordEntity;

/**
 * Created by Bohdan on 04/08/2014.
 */
public class ResetPasswordService {

    @Autowired
    private ResetPasswordDao resetPasswordDao;

    public ResetPasswordEntity createHash(String email) {
        return resetPasswordDao.save(new ResetPasswordEntity("111111", email));
    }
}
