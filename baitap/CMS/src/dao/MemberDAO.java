package dao;

import model.Member;
import util.DBConnection;
import java.sql.*;

public class MemberDAO {

    public Member login(String username, String password) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM Member WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
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

    public void register(Member m) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO Member(username,password,email) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getUsername());
            ps.setString(2, m.getPassword());
            ps.setString(3, m.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
