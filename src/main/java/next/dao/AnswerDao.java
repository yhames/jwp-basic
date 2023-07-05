package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;
import next.model.Question;

import java.sql.Timestamp;
import java.util.List;

public class AnswerDao {
    public Long insert(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO answers VALUES (?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                null,
                answer.getWriter(),
                answer.getContents(),
                Timestamp.valueOf(answer.getCreatedDate()),
                answer.getQuestionId());
    }

    public Long update(Answer updateAnswer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE answers SET writer=?, contents=? WHERE answerId=?";
        return jdbcTemplate.update(sql,
                updateAnswer.getWriter(),
                updateAnswer.getContents(),
                updateAnswer.getAnswerId());
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