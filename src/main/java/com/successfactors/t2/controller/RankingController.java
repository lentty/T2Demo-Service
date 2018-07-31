package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Points;
import com.successfactors.t2.service.RankingService;
import com.successfactors.t2.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/ranking")
public class RankingController {
    @Autowired
    private RankingService rankingService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseBody
    public List<Points> getRankingList(){
        String beginDate = "2018-07-31";
        String endDate = DateUtil.formatDate();
        return rankingService.getRankingListByPeriod(beginDate, endDate);
    }
}
