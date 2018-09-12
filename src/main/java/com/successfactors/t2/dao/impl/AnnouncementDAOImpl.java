package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.AnnouncementDAO;
import com.successfactors.t2.domain.Announcement;
import com.successfactors.t2.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class AnnouncementDAOImpl implements AnnouncementDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int editAnnouncement(Announcement announcement) {
        Integer id = announcement.getId();
        String currentDate = DateUtil.formatDateTime(new Date());
        if (id == null) {
            String insertSql = "insert into announcement (content, created_by, created_date, " +
                    "last_modified_by, last_modified_date) values (?, ?, ?, ?, ?)";
            return jdbcTemplate.update(insertSql, new Object[]{announcement.getContent(), announcement.getCreatedBy(),
                    currentDate, announcement.getLastModifiedBy(), currentDate});
        } else {
            String updateSql = "update announcement set content = ?, last_modified_by = ?, last_modified_date = ?" +
                    " where id = ?";
            return jdbcTemplate.update(updateSql, new Object[]{announcement.getContent(), announcement.getLastModifiedBy(),
                    currentDate, announcement.getId()});
        }
    }

    @Override
    public List<Announcement> getAnnouncementList() {
        String query = "select id, content, last_modified_date from announcement order by " +
                " last_modified_date desc";
        return jdbcTemplate.query(query, new RowMapper<Announcement>() {
            @Override
            public Announcement mapRow(ResultSet resultSet, int i) throws SQLException {
                Announcement announcement = new Announcement();
                announcement.setId(resultSet.getInt("id"));
                announcement.setContent(resultSet.getString("content"));
                announcement.setLastModifiedDate(resultSet.getString("last_modified_date"));
                return announcement;
            }
        });
    }
}
