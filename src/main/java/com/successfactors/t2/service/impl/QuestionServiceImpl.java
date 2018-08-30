package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.QuestionDAO;
import com.successfactors.t2.domain.Question;
import com.successfactors.t2.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionDAO questionDAO;

    @Override
    public int editQuestion(Question question) {
        return questionDAO.editQuestion(question);
    }
}
