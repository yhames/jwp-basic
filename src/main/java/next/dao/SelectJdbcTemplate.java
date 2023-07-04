package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectJdbcTemplate {

    List query() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQuery();
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);
            rs = pstmt.executeQuery();

            List<Object> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    Object queryForObject() throws SQLException {
        List users = query();
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    abstract String createQuery();

    abstract void setValues(PreparedStatement pstmt) throws SQLException;

    abstract Object mapRow(ResultSet rs) throws SQLException;
}