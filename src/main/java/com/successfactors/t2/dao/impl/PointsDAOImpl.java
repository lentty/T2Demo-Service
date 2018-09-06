package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.PointsDAO;
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
        String query = "select a.id, a.nickname, a.avatarUrl, a.initial_points + if(b.session_points is null, 0, b.session_points)  as total_points " +
                  "from  (select id, nickname, avatarUrl, initial as initial_points from user where status = 1) a " +
                  "left outer join " +
                  "  (select p.user_id, sum(checkin)+sum(host)+sum(exam)+sum(lottery) as session_points from points p, session s " +
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
        return jdbcTemplate.update("update points set lorettery = 5 where session_id = ? and bet_number = ?",
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updatePointsForExam(Integer sessionId, String userId, int points) {
        int sessionCount = getSessionCount(sessionId, userId);
        if (sessionCount == 0) {
            return jdbcTemplate.update("insert into points(user_id, session_id, exam) values (?, ?, ?)",
                    new Object[]{userId, sessionId, points});
        } else {
            String updateSql = "update points set exam = ? where session_id = ? and user_id = ?";
            return jdbcTemplate.update(updateSql, new Object[]{points, sessionId, userId});
        }
    }
}
