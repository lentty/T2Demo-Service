package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.UserDAO;
import com.successfactors.t2.domain.User;
import com.successfactors.t2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDAO userDAO;

    @Override
    public int addUser(User user){
        return userDAO.addUser(user);
    }

}
