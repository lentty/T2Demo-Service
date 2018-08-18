package com.successfactors.t2.service;

import com.successfactors.t2.domain.Session;

import java.util.List;

public interface SessionService {
    List<Session> getSessionList();
    Session getSessionByDate(String date);
}
