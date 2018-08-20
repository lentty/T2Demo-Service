package com.successfactors.t2.service;

public interface CheckinService {

    String generateCheckinCode(Integer sessionId, String userId);
}
