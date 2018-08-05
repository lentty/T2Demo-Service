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
        String query = "select u.id, u.nickname, u.avatarUrl, sum(p.points) as total_points from points p, event e, user u where " +
                "p.event_id = e.id and p.userid = u.id and e.event_date >= ? and e.event_date <= ? group by p.userid order by total_points desc";

        return jdbcTemplate.query(query, new Object[]{beginDate, endDate}, new RowMapper<RankingItem>() {
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
