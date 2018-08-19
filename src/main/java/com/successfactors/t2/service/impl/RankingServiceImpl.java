package com.successfactors.t2.service.impl;

import com.successfactors.t2.dao.PointsDAO;
import com.successfactors.t2.domain.RankingItem;
import com.successfactors.t2.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingServiceImpl implements RankingService{
    @Autowired
    private PointsDAO pointsDAO;

    @Override
    public List<RankingItem> getRankingListByPeriod(String beginDate, String endDate){
         return pointsDAO.getUserRankingList(beginDate, endDate);
    }

    @Override
    public int updatePointsForHost(Integer sessionId, String userId) {
        return pointsDAO.updatePointsForHost(sessionId, userId);
    }

    @Override
    public int updatePointsForCheckin(Integer sessionId, String userId) {
        return pointsDAO.updatePointsForCheckin(sessionId, userId);
    }
}
