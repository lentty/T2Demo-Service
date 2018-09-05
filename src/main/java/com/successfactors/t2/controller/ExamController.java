package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Question;
import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.SessionVO;
import com.successfactors.t2.service.QuestionService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/load/question/{sessionId}", method = RequestMethod.GET)
    public Result loadQuestionsBySession(@PathVariable("sessionId") Integer sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        List<Question> questionList = questionService.loadQuestions(sessionId, 1);
        return new Result(0, Constants.SUCCESS, questionList);
    }

    @RequestMapping(value = "/load/session", method = RequestMethod.GET)
    public Result loadHistorySessions() {
        List<SessionVO> sessions = sessionService.loadHistorySessions();
        return new Result(0, Constants.SUCCESS, sessions);
    }

}
