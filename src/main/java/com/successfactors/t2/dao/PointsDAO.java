package com.successfactors.t2.dao;

import com.successfactors.t2.domain.RankingItem;

import java.util.List;

public interface PointsDAO {
    List<RankingItem> getUserRankingList(String beginDate, String endDate);
    int updatePointsForHost(Integer sessionId, String userId);
    int updatePointsForCheckin(Integer sessionId, String userId);
    int updatePointsForLottery(Integer sessionId, Integer luckyNumber);
}
