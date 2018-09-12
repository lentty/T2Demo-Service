package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.PointsDAO;
import com.successfactors.t2.domain.Points;
import com.successfactors.t2.domain.RankingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PointsDAOImpl implements PointsDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RankingItem> getUserRankingList(String beginDate, String endDate){
        String query = "select a.id, a.nickname, a.avatarUrl, a.initial_points + ifnull(b.session_points, 0)  as total_points " +
                  "from  (select id, nickname, avatarUrl, initial as initial_points from user where status != 0) a " +
                  "left outer join " +
                  "  (select p.user_id, sum(checkin)+sum(host)+sum(ifnull(exam,0))+sum(lottery) as session_points from points p, session s " +
                  " where p.session_id = s.id and s.season = 'S1' group by p.user_id) b " +
                  "on a.id = b.user_id order by total_points desc";

        return jdbcTemplate.query(query, new RowMapper<RankingItem>() {
            @Override
            public RankingItem mapRow(ResultSet resultSet, int i) throws SQLException {
                RankingItem rankingItem = new RankingItem();
                rankingItem.setRank(i+1);
                rankingItem.setUserId(resultSet.getString("id"));
                rankingItem.setNickname(resultSet.getString("nickname"));
                rankingItem.setAvatarUrl(resultSet.getString("avatarUrl"));
                rankingItem.setPoints(resultSet.getInt("total_points"));
                return rankingItem;
            }
        });
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForHost(Integer sessionId, String userId) {
        int sessionCount = getSessionCount(sessionId, userId);
        if (sessionCount == 0) {
            return jdbcTemplate.update("insert into points(user_id, session_id, host) values (?, ?, ?)",
                    new Object[]{userId, sessionId, 5});
        } else {
            return jdbcTemplate.update("update points set host = 5 where user_id = ? and session_id = ? ",
                    new Object[]{userId, sessionId});
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForCheckin(Integer sessionId, String userId) {
        int sessionCount = getSessionCount(sessionId, userId);
        if (sessionCount == 0) {
            return jdbcTemplate.update("insert into points(user_id, session_id, checkin) values (?, ?, ?)",
                    new Object[]{userId, sessionId, 1});
        } else {
            return jdbcTemplate.update("update points set checkin = 1 where user_id = ? and session_id = ? ",
                    new Object[]{userId, sessionId});
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForLottery(Integer sessionId, Integer luckyNumber) {
        jdbcTemplate.update("update points set lottery = 0 where session_id = ? and bet_number != ?",
                new Object[]{sessionId, luckyNumber});
        return jdbcTemplate.update("update points set lottery = 5 where session_id = ? and bet_number = ?",
                new Object[]{sessionId, luckyNumber});
    }

    private int getSessionCount(Integer sessionId, String userId){
        return jdbcTemplate.queryForObject("select count(1) as cnt from points where session_id = ?" +
                " and user_id = ?", new Object[]{sessionId, userId}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
    }

    @Override
    public Points getPointsById(Integer sessionId, String userId){
        List<Points> pointsList = jdbcTemplate.query("select user_id, session_id, bet_number, checkin, host, exam, lottery from points where session_id = ?" +
                " and user_id = ?", new Object[]{sessionId, userId}, new RowMapper<Points>() {
            @Override
            public Points mapRow(ResultSet resultSet, int i) throws SQLException {
                Points point = new Points();
                point.setUserId(resultSet.getString("user_id"));
                point.setSessionId(resultSet.getInt("session_id"));
                point.setBetNumber(resultSet.getInt("bet_number"));
                point.setCheckin(resultSet.getInt("checkin"));
                point.setHost(resultSet.getInt("host"));
                Object exam = resultSet.getObject("exam");
                if (exam == null) {
                    point.setExam(null);
                } else {
                    point.setExam((Integer) exam);
                }
                point.setLottery(resultSet.getInt("lottery"));
                return point;
            }
        });
        return pointsList.size() == 0 ? null : pointsList.get(0);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForExam(Integer sessionId, String userId, int points) {
        Points point = getPointsById(sessionId, userId);
        if (point == null) {
            return jdbcTemplate.update("insert into points(user_id, session_id, exam) values (?, ?, ?)",
                    new Object[]{userId, sessionId, points});
        } else {
            if (point.getExam() == null) {
                String updateSql = "update points set exam = ? where session_id = ? and user_id = ?";
                return jdbcTemplate.update(updateSql, new Object[]{points, sessionId, userId});
            } else {
                return -1;
            }
        }
    }
}
