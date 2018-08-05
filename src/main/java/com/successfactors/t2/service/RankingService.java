package com.successfactors.t2.service;

import com.successfactors.t2.domain.RankingItem;

import java.util.List;

public interface RankingService {

    List<RankingItem> getRankingListByPeriod(String beginDate, String endDate);

}
