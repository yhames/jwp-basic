package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Question;

import java.sql.Timestamp;
import java.util.List;

public class QuestionDao {

    public Long insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO questions VALUES (?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                null,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                Timestamp.valueOf(question.getCreatedDate()),
                0);
    }

    public Long update(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE questions SET writer=?, title=?, contents=? WHERE questionId=?";
        return jdbcTemplate.update(sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getQuestionId());
    }

    // TODO : countOfAnswer 관련해서 JOIN 해서 가져올지, answer insert 될때마다 question update query 보낼지
    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM questions";
        RowMapper<Question> rowMapper = (rs) -> new Question(
                Long.parseLong(rs.getString("questionId")),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                Timestamp.valueOf(rs.getString("createdDate")).toLocalDateTime(),
                Integer.parseInt(rs.getString("countOfAnswer")));
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Question findById(Long questionId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM questions WHERE questionId=?";
        RowMapper<Question> rowMapper = (rs) -> new Question(
                Long.parseLong(rs.getString("questionId")),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                Timestamp.valueOf(rs.getString("createdDate")).toLocalDateTime(),
                Integer.parseInt(rs.getString("countOfAnswer")));
        return jdbcTemplate.queryForObject(sql, rowMapper, questionId);
    }
}
