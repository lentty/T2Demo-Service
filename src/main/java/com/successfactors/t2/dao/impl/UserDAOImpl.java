package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.UserDAO;
import com.successfactors.t2.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addUser(UserInfo userInfo) {
        String id = userInfo.getId();
        Integer userCnt = jdbcTemplate.queryForObject("select count(*) as cnt from user where id = ?", new Object[]{id}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("cnt");
            }
        });
        if (userCnt == 0) {
            return jdbcTemplate.update("insert into user(id, nickname, gender, avatarUrl) values (?, ?, ?, ?)",
                    new Object[]{id, userInfo.getNickName(), userInfo.getGender(), userInfo.getAvatarUrl()});
        } else if (userCnt == 1) {
            return jdbcTemplate.update("update user set nickname = ?, gender = ?, avatarUrl = ? where id = ?",
                    new Object[]{userInfo.getNickName(), userInfo.getGender(), userInfo.getAvatarUrl(), id});
        }
        return -1;
    }
}
