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
}
