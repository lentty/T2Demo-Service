package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public List<Session> getSessionList(){
        return sessionService.getSessionList();
    }
}
