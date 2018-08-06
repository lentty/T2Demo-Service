package com.successfactors.t2.controller;

import com.successfactors.t2.domain.RankingItem;
import com.successfactors.t2.service.RankingService;
import com.successfactors.t2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private RankingService rankingService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public List<RankingItem> getRankingList(){
        String beginDate = "2018-07-31";
        String endDate = DateUtil.formatDate();
        return rankingService.getRankingListByPeriod(beginDate, endDate);
    }
}
