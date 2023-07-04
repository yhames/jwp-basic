package next.dao;

import next.model.User;

import java.util.List;

public class UserDao {
    public void insert(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.execute(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        JdbcTemplate updateJdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
        updateJdbcTemplate.execute(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper<User> rowMapper = rs -> new User(rs.getString("userId"), rs.getString("password"),
                rs.getString("name"), rs.getString("email"));
        String sql = "SELECT userId, password, name, email FROM USERS";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public User findByUserId(String userId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper<User> rowMapper = rs -> new User(rs.getString("userId"), rs.getString("password"),
                rs.getString("name"), rs.getString("email"));
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, userId);
    }
}