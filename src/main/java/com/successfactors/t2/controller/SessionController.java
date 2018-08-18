package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/{date}", method = RequestMethod.GET)
    @ResponseBody
    public Result getSessionByDate(@PathVariable("date") String date){
        Session session = sessionService.getSessionByDate(date);
        if(session == null){
            return new Result(0, "no_session");
        }else{
            return new Result(0, "ok", session);
        }
    }
}
