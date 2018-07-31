package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.PointsDAO;
import com.successfactors.t2.domain.Points;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class PointsDAOImpl implements PointsDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Points> getUserRankingList(String beginDate, String endDate){
        String query = "select p.userid, sum(p.points) as total_points from points p, event e where " +
                "p.event_id = e.id and e.event_date >= ? and e.event_date <= ? group by p.userid order by total_points desc";

        return jdbcTemplate.query(query, new Object[]{beginDate, endDate}, new RowMapper<Points>() {
            @Override
            public Points mapRow(ResultSet resultSet, int i) throws SQLException {
                Points points = new Points();
                points.setUserId(resultSet.getString("userid"));
                points.setPoints(resultSet.getInt("total_points"));
                return points;
            }
        });
    }
}
