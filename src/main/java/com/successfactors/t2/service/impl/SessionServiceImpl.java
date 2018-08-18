package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.SessionDAO;
import com.successfactors.t2.domain.Session;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private SessionDAO sessionDAO;

    @Override
    public List<Session> getSessionList() {
        List<Session> sessionList = sessionDAO.getSessionList();
        if (sessionList != null) {
            String currentDate = DateUtil.formatDate();
            boolean findNext = false;
            for (Session session : sessionList) {
                String sessionDate = session.getSessionDate();
                if (sessionDate.compareTo(currentDate) < 0) {
                    session.setStatus(1);
                } else {
                    if (!findNext) {
                        session.setStatus(2);
                        findNext = true;
                    } else {
                        session.setStatus(3);
                    }
                }
            }
        }
        return sessionList;
    }

    @Override
    public Session getSessionByDate(String date){
        return sessionDAO.getSessionByDate(date);
    }
}
