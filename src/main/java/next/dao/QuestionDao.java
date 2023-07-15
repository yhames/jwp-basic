package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import core.jdbc.RowMapper;
import next.model.Question;

public class QuestionDao {

    private static final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    private static QuestionDao questionDao;

    private QuestionDao() {

    }

    public static QuestionDao getInstance() {
        if (questionDao == null) {
            questionDao = new QuestionDao();
        }
        return questionDao;
    }

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS " +
                "(writer, title, contents, createdDate) " +
                " VALUES (?, ?, ?, ?)";
        PreparedStatementCreator psc = con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
            return pstmt;
        };

        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getId());
    }

    public List<Question> findAll() {
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                null,
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer"));

        return jdbcTemplate.query(sql, rm);
    }

    public Question findById(long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";

        RowMapper<Question> rm = rs -> new Question(rs.getLong("questionId"),
                rs.getString("writer"),
                rs.getString("title"),
                rs.getString("contents"),
                rs.getTimestamp("createdDate"),
                rs.getInt("countOfAnswer"));

        return jdbcTemplate.queryForObject(sql, rm, questionId);
    }

    public void update(Question updateQuestion) {
        String sql = "UPDATE questions SET title=?, contents=? WHERE questionId=?";
        jdbcTemplate.update(sql,
                updateQuestion.getTitle(),
                updateQuestion.getContents(),
                updateQuestion.getQuestionId());
    }

    public void updateCountOfAnswer(long questionId) {
        String sql = "UPDATE questions as q" +
                " SET q.countOfAnswer = (SELECT COUNT(a.questionId) FROM answers as a WHERE a.questionId=?)" +
                " WHERE q.questionId=?";
        jdbcTemplate.update(sql, questionId, questionId);
    }

    public void delete(long questionId) {
        String sql = "DELETE FROM questions WHERE questionId=?";
        jdbcTemplate.update(sql, questionId);
    }
}
