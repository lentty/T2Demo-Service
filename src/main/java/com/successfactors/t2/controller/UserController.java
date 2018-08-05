package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.UserInfo;
import com.successfactors.t2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result addUser(@RequestBody UserInfo userInfo) {
        if (userInfo.getId() == null || userInfo.getNickName() == null || userInfo.getGender() == null) {
            String errorMsg = "user info is not correct";
            System.out.println(errorMsg);
            return new Result(-1, errorMsg);
        }
        int status = userService.addUser(userInfo);
        if (status > 0) {
            return new Result(status, "ok");
        } else {
            return new Result(-1, "error occurs");
        }
    }

}
