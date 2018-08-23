package com.successfactors.t2.dao;

import com.successfactors.t2.domain.Session;

import java.util.List;

public interface SessionDAO {
    List<Session> getSessionList();

    Session getSessionByDate(String date);

    Session getSessionByOwner(String userId);

    int updateCheckinCode(Integer sessionId, String checkinCode);
}
