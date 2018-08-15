package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.PointsDAO;
import com.successfactors.t2.domain.RankingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PointsDAOImpl implements PointsDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RankingItem> getUserRankingList(String beginDate, String endDate){
        String query = "select u.id, u.nickname, u.avatarUrl, initial as total_points from user u where u.status = 1 order by total_points desc";

        return jdbcTemplate.query(query, new RowMapper<RankingItem>() {
            @Override
            public RankingItem mapRow(ResultSet resultSet, int i) throws SQLException {
                RankingItem rankingItem = new RankingItem();
                rankingItem.setUserId(resultSet.getString("id"));
                rankingItem.setNickname(resultSet.getString("nickname"));
                rankingItem.setAvatarUrl(resultSet.getString("avatarUrl"));
                rankingItem.setPoints(resultSet.getInt("total_points"));
                return rankingItem;
            }
        });
    }
}
