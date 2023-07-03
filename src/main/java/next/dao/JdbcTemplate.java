package next.dao;

import core.jdbc.ConnectionManager;
import org.h2.result.Row;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    void execute(String sql, Object... parameters) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException();
        }
    }

    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
        ResultSet rs = null;
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            rs = pstmt.executeQuery();
            List<T> list = new ArrayList<T>();
            while (rs.next()) {
                list.add(rowMapper.mapRow(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DataAccessException();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DataAccessException();
                }
            }
        }
    }

    <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... parameters) {
        List<T> result = query(sql, rowMapper, parameters);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}