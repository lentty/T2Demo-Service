package com.successfactors.t2.dao;

import com.successfactors.t2.domain.Points;

import java.util.List;

public interface PointsDAO {
    List<Points> getUserRankingList(String beginDate, String endDate);

}
