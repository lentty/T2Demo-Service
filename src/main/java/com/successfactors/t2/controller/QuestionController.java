package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Question;
import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.QuestionService;
import com.successfactors.t2.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionService sessionService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public Result editQuestion(@RequestBody Question question) {
        if (question == null) {
            String errorMsg = "input parameter is null";
            logger.info(errorMsg);
            return new Result(-1, errorMsg);
        }
        String userId = question.getOwner();
        if (userId != null) {
            Session session = sessionService.getSessionByOwner(userId);
            if (session != null) {
                question.setSessionId(session.getSessionId());
                int status = questionService.editQuestion(question);
                return new Result(status, "ok");
            } else {
                return new Result(-1, "no_session");
            }
        } else {
            return new Result(-1, "empty_userId");
        }
    }

}
