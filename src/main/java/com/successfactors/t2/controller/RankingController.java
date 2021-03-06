package com.successfactors.t2.controller;

import com.successfactors.t2.domain.Points;
import com.successfactors.t2.domain.RankingItem;
import com.successfactors.t2.domain.Result;
import com.successfactors.t2.domain.User;
import com.successfactors.t2.service.RankingService;
import com.successfactors.t2.service.SessionService;
import com.successfactors.t2.service.UserService;
import com.successfactors.t2.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RankingService rankingService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public List<RankingItem> getRankingList(){
        String season = sessionService.getCurrentSeason();
        logger.info("Current season: " + season);
        return rankingService.getRankingListBySeason(season);
    }

    @RequestMapping(value="/points/{userId}", method = RequestMethod.GET)
    public Result getPointsDetailForUser(@PathVariable("userId") String userId){
        if(StringUtils.isEmpty(userId)){
            return new Result(-1, Constants.ILLEGAL_ARGUMENT);
        }
        User user = userService.getUserById(userId);
        if(user != null){
            int initialPoints = user.getInitialPoints();
            List<Points> pointsList = rankingService.getPointsDetailForUser(userId);
            return new Result(initialPoints, Constants.SUCCESS, pointsList);
        }
        return new Result(-1, Constants.NOT_AUTHORIZED);
    }


}
