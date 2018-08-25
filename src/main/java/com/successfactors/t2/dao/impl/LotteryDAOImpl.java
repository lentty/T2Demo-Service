package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.LotteryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LotteryDAOImpl implements LotteryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int bet(String userId, Integer sessionId, Integer number) {
        return jdbcTemplate.update("update points set bet_number = ? where user_id = ? and session_id = ?",
                new Object[]{number, userId, sessionId});
    }

}
