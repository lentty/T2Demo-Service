package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.LotteryDAO;
import com.successfactors.t2.service.LotteryService;
import com.successfactors.t2.service.RankingService;
import com.successfactors.t2.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.Set;

@Service
public class LotteryServiceImpl implements LotteryService{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LotteryDAO lotteryDAO;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RankingService rankingService;

    @Override
    public int bet(String userId, Integer sessionId, Integer number) {
        return lotteryDAO.bet(userId, sessionId, number);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int draw(String userId, Integer sessionId) {
        int number = getLuckyNumber();
        logger.info("Lucky number is: " + number);
        if (number > 0) {
            sessionService.updateLuckyNumber(sessionId, number);
            rankingService.updatePointsForLottery(sessionId, number);
        }
        return number;
    }

    private int getLuckyNumber(){
        Set<String> userList = sessionService.getAttendeeList();
        if(userList != null && userList.size() > 0){
            int size = userList.size();
            Random random = new Random();
            return random.nextInt(size) + 1;
        }
        return 0;
    }
}
