package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.domain.User;
import com.successfactors.t2.service.CheckinService;
import com.successfactors.t2.service.RankingService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.service.UserService;
import com.successfactors.t2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CheckinService checkinService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RankingService rankingService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result addUser(@RequestBody User user) {
        if (user.getId() == null || user.getNickName() == null || user.getGender() == null) {
            String errorMsg = "user info is not correct";
            System.out.println(errorMsg);
            return new Result(-1, errorMsg);
        }
        int status = userService.addUser(user);
        if (status > 0) {
            return new Result(status, "ok");
        } else {
            return new Result(-1, "error occurs");
        }
    }

    @RequestMapping(value = "/checkinCode/{userId}", method = RequestMethod.GET)
    public Result generateCheckinCode(@PathVariable("userId") String userId) {
        if(userId != null){
            Session session = sessionService.getSessionByDate(DateUtil.formatDate());
            if (session != null && userId.equals(session.getOwner())) {
                String code = checkinService.getCheckinCode();
                if (code != null) {
                    int status = sessionService.updateCheckinCode(session.getSessionId(), code);
                    status += rankingService.updatePointsForHost(session.getSessionId(), userId);
                    return new Result(status, "ok", code);
                } else {
                    return new Result(0, "no_code", null);
                }
            } else {
                return new Result(-1, "not_authorized", null);
            }
        }
        return new Result(-1, "illegal_argument", null);
    }

    @RequestMapping(value = "/checkin/{userId}/{code}", method = RequestMethod.GET)
    public Result checkin(@PathVariable("userId") String userId, @PathVariable("code") String code) {
        if (userId == null || code == null) {
            return new Result(-1, "illegal_argument", null);
        }
        Session session = sessionService.getSessionByDate(DateUtil.formatDate());
        if (session != null && !userId.equals(session.getOwner())) {
            if (code.equalsIgnoreCase(session.getCheckinCode())) {
                int status = rankingService.updatePointsForCheckin(session.getSessionId(), userId);
                return new Result(status, "ok", null);
            } else {
                return new Result(-1, "wrong_code", null);
            }
        }
        return new Result(-1, "not_authorized", null);
    }

}
