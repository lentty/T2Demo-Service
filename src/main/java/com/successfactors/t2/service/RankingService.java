package com.successfactors.t2.service;

import com.successfactors.t2.domain.Points;

import java.util.Date;
import java.util.List;

public interface RankingService {

    List<Points> getRankingListByPeriod(String beginDate, String endDate);

}
