package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.UserDAO;
import com.successfactors.t2.domain.User;
import com.successfactors.t2.service.CacheService;
import com.successfactors.t2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CacheService cacheService;

    @Override
    public int addUser(User user){
        Set<String> userList = cacheService.getUserToSessionCache().keySet();
        if (userList != null && userList.contains(user.getId())) {
            user.setStatus(1);
        } else {
            user.setStatus(0);
        }
        userDAO.addUser(user);
        return user.getStatus();
    }

}
