package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Question;
import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.QuestionService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.Constants;
import com.successfactors.t2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/load/{userId}", method = RequestMethod.GET)
    public Result loadQuestions(@PathVariable("userId") String userId) {
        if (StringUtils.isEmpty(userId)) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        String today = DateUtil.formatDate(new Date());
        Session session = sessionService.getSessionByDate(today);
        if (session != null) {
            List<Question> questionList = questionService.loadQuestions(session.getSessionId(), 1);
            return new Result(0, Constants.SUCCESS, questionList);
        } else {
            return new Result(-1, Constants.NOT_AUTHORIZED);
        }
    }

}
