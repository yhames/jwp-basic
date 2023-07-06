package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Answer;

import java.sql.Timestamp;
import java.util.List;

public class AnswerDao {
    public Long insert(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO answers (writer, contents, createdDate, questionId) VALUES (?,?,?,?)";
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, answer.getWriter());
            pstmt.setString(2, answer.getContents());
            pstmt.setTimestamp(3, Timestamp.valueOf(answer.getCreatedDate()));
            pstmt.setLong(4, answer.getQuestionId());
        };
        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(sql, pss, keyHolder);
        return keyHolder.getKey();
    }

    public Long update(Answer updateAnswer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE answers SET writer=?, contents=? WHERE answerId=?";
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, updateAnswer.getWriter());
            pstmt.setString(2, updateAnswer.getContents());
            pstmt.setLong(3, updateAnswer.getAnswerId());
        };
        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(sql, pss, keyHolder);
        return keyHolder.getKey();
    }

    public List<Answer> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM answers";
        RowMapper<Answer> rowMapper = (rs) -> new Answer(
                Long.parseLong(rs.getString("answerId")),
                rs.getString("writer"),
                rs.getString("contents"),
                Timestamp.valueOf(rs.getString("createdDate")).toLocalDateTime(),
                Long.parseLong(rs.getString("questionId")));
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Answer> findAllByQuestionId(Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId"
                + " FROM answers"
                + " WHERE questionId=?"
                + " ORDER BY answerId DESC";
        RowMapper<Answer> rowMapper = (rs) -> new Answer(
                Long.parseLong(rs.getString("answerId")),
                rs.getString("writer"),
                rs.getString("contents"),
                Timestamp.valueOf(rs.getString("createdDate")).toLocalDateTime(),
                Long.parseLong(rs.getString("questionId")));
        return jdbcTemplate.query(sql, rowMapper, questionId);
    }

    public Answer findById(Long answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM answers WHERE answerId=?";
        RowMapper<Answer> rowMapper = (rs) -> new Answer(
                Long.parseLong(rs.getString("answerId")),
                rs.getString("writer"),
                rs.getString("contents"),
                Timestamp.valueOf(rs.getString("createdDate")).toLocalDateTime(),
                Long.parseLong(rs.getString("questionId")));
        return jdbcTemplate.queryForObject(sql, rowMapper, answerId);
    }
}