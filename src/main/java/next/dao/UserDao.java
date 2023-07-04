package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            String createQuery() {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }
        };
        jdbcTemplate.execute();
    }

    public void update(User user) throws SQLException {
        JdbcTemplate updateJdbcTemplate = new JdbcTemplate() {
            @Override
            String createQuery() {
                return "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }
        };
        updateJdbcTemplate.execute();
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            String createQuery() {
                return "SELECT userId, password, name, email FROM USERS";
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {

            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"),
                        rs.getString("name"), rs.getString("email"));
            }
        };
        return jdbcTemplate.query();
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            String createQuery() {
                return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
        };
        return (User) jdbcTemplate.queryForObject();
    }
}