package ua.com.itinterview.dao;

import org.springframework.transaction.annotation.Transactional;
import ua.com.itinterview.entity.QuestionEntity;
import ua.com.itinterview.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends EntityWithIdDao<QuestionEntity> {

    @Transactional
    public List<QuestionEntity> getQuestionsForUser(UserEntity user) {
        ArrayList<QuestionEntity> questions = new ArrayList<QuestionEntity>();
        return questions;
    }

}
