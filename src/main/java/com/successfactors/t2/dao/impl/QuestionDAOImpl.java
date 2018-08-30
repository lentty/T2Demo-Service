package com.successfactors.t2.dao.impl;

import com.successfactors.t2.dao.QuestionDAO;
import com.successfactors.t2.domain.Option;
import com.successfactors.t2.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDAOImpl implements QuestionDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int editQuestion(Question question) {
        Integer questionId = question.getId();
        if (questionId == null) {
            logger.info("add question");
            return addQuestion(question);
        } else {
            logger.info("update question " + questionId);
            return updateQuestion(question);
        }
    }

    private int updateQuestion(Question question) {
        int status = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String updateSql = "update question set content = ? where id = ?";
                PreparedStatement ps = connection.prepareStatement(updateSql);
                int index = 1;
                ps.setString(index++, question.getContent());
                ps.setInt(index, question.getId());
                return ps;
            }
        });
        String deleteSql = "delete from `option` where question_id = ?";
        int deleteCount = jdbcTemplate.update(deleteSql, question.getId());
        logger.info("delete " + deleteCount + " records in option table");
        insertOptions(question, question.getId());
        return status;
    }

    private int addQuestion(Question question) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int status = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String insertSql = "insert into question(session_id, content) values (?, ?)";
                PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                ps.setInt(index++, question.getSessionId());
                ps.setString(index, question.getContent());
                return ps;
            }
        }, keyHolder);
        int generatedId = keyHolder.getKey().intValue();
        if (generatedId > 0) {
            insertOptions(question, generatedId);
        }
        return status;
    }

    private int[] insertOptions(Question question, Integer questionId) {
        if (!CollectionUtils.isEmpty(question.getOptions())) {
            String insertSql = "insert into `option`(question_id, number, content, is_answer) values (?, ?, ?, ?)";
            List<Object[]> bacthArgs = new ArrayList<>();
            for (Option option : question.getOptions()) {
                Object[] args = new Object[]{questionId, option.getNumber(), option.getContent(), option.getIsAnswer()};
                bacthArgs.add(args);
            }
            int[] insertCount = jdbcTemplate.batchUpdate(insertSql, bacthArgs);
            logger.info("insert " + insertCount.length + " records into option table");
            return insertCount;
        }
        return new int[]{};
    }
}
