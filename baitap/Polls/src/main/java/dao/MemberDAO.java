package dao;

import util.DBConnection;
import model.Member;
import java.sql.*;

public class MemberDAO {

    public Member login(String u, String p) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM member WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u);
            ps.setString(2, p);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Member m = new Member();
                m.setId(rs.getInt("id"));
                m.setUsername(rs.getString("username"));
                return m;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean register(Member m) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO member(username,password,email) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getUsername());
            ps.setString(2, m.getPassword());
            ps.setString(3, m.getEmail());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}