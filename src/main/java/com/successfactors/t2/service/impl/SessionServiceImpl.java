package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.SessionDAO;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService{
    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public List<Session> getSessionList() {
        List<Session> sessionList =  sessionDAO.getSessionList();
        if(sessionList != null){
            String currentDate = DateUtil.formatDate();
            for(Session session : sessionList){
                if(session.getSessionDate().compareTo(currentDate) > 0){
                    session.setNext(true);
                    break;
                }
            }
        }
        return sessionList;
    }
}
