package com.successfactors.t2.service;

public interface LotteryService {

    int bet(String userId, Integer sessionId, Integer number);

    int draw(String userId, Integer sessionId);
}
