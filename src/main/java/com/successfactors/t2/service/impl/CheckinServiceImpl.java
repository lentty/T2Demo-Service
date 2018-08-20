package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.CheckinDAO;
import com.successfactors.t2.service.CheckinService;
import com.successfactors.t2.service.RankingService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.utils.CheckinCodeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Random;

@Service
public class CheckinServiceImpl implements CheckinService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckinDAO checkinDAO;

    @Autowired
    private SessionService  sessionService;

    @Autowired
    private RankingService rankingService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String generateCheckinCode(Integer sessionId, String userId) {
        String code = getCheckinCode();
        if (code != null) {
            sessionService.updateCheckinCode(sessionId, code);
            rankingService.updatePointsForHost(sessionId, userId);
            return code;
        } else {
            return null;
        }
    }

    private String getCheckinCode() {
        Map<Integer, String> codeMap;
        if (CheckinCodeFactory.codeMap.isEmpty()) {
            logger.info("get from db");
            codeMap = checkinDAO.getAllCodes();
        } else {
            logger.info("get from cache");
            codeMap = CheckinCodeFactory.codeMap;
        }
        int codeSize = codeMap.size();
        Random random = new Random();
        Integer number = random.nextInt(codeSize) + 1;
        logger.info("code number:" + number);
        return codeMap.get(number);
    }
}
