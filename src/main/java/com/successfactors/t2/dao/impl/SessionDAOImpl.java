package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.SessionDAO;
import com.successfactors.t2.domain.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SessionDAOImpl implements SessionDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Session> getSessionList() {
       String query = "select s.id, s.owner, s.date, u.nickname, u.avatarUrl, u.department from user u, session s where "+
                " s.owner = u.id and s.season ='S1' order by s.date";
        return jdbcTemplate.query(query, new RowMapper<Session>() {
            @Override
            public Session mapRow(ResultSet resultSet, int i) throws SQLException {
                Session session = new Session();
                session.setSessionId(resultSet.getInt("id"));
                session.setOwner(resultSet.getString("owner"));
                session.setSessionDate(resultSet.getString("date"));
                session.setNickname(resultSet.getString("nickname"));
                session.setAvatarUrl(resultSet.getString("avatarUrl"));
                session.setDepartment(resultSet.getString("department"));
                return session;
            }
        });
    }

    @Override
    public Session getSessionByDate(String date){
       String query = "select owner, date from session where date = ?";
       List<Session> sessions = jdbcTemplate.query(query, new Object[]{date}, new RowMapper(){
           @Override
           public Session mapRow(ResultSet resultSet, int i) throws SQLException {
              Session session = new Session();
              session.setOwner(resultSet.getString("owner"));
              session.setSessionDate(resultSet.getString("date"));
              return session;
           }
       });
       if (sessions != null && !sessions.isEmpty()){
           return sessions.get(0);
       }else {
           return null;
       }
    }
}
