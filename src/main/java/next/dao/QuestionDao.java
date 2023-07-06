package next.dao;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementSetter;
import core.jdbc.RowMapper;
import next.model.Question;

import java.sql.Timestamp;
import java.util.List;

public class QuestionDao {

    public Long insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO questions (writer, title, contents, createdDate, countOfAnswer) VALUES (?,?,?,?,?)";
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, Timestamp.valueOf(question.getCreatedDate()));
            pstmt.setLong(5, 0L);
        };
        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(sql, pss, keyHolder);
        return keyHolder.getKey();
    }

    public Long update(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE questions SET writer=?, title=?, contents=? WHERE questionId=?";
        PreparedStatementSetter pss = pstmt -> {
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setLong(4, question.getQuestionId());
        };
        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(sql, pss, keyHolder);
        return keyHolder.getKey();
    }

    public List<Question> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT q.questionId, q.writer, q.title, q.contents, q.createdDate, COUNT(a.questionId) AS countOfAnswer\n" +
                "FROM questions AS q\n" +
                "LEFT OUTER JOIN answers AS a\n" +
                "ON q.questionId = a.questionId\n" +
                "GROUP BY q.questionId";
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
        String sql = "SELECT q.questionId, q.writer, q.title, q.contents, q.createdDate, COUNT(a.questionId) AS countOfAnswer\n" +
                "FROM questions AS q\n" +
                "LEFT OUTER JOIN answers AS a\n" +
                "ON q.questionId = a.questionId\n" +
                "WHERE q.questionId=?";
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
