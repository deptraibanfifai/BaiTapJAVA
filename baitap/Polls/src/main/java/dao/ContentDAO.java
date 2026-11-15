package dao;

import util.DBConnection;
import model.Content;
import java.sql.*;
import java.util.*;

public class ContentDAO {

    public boolean insert(Content c) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO content(title,brief,content,author_id) VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, c.getTitle());
            ps.setString(2, c.getBrief());
            ps.setString(3, c.getContent());
            ps.setInt(4, c.getAuthorId());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Content> getAll(int userId) {
        List<Content> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM content WHERE author_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Content c = new Content();
                c.setId(rs.getInt("id"));
                c.setTitle(rs.getString("title"));
                c.setBrief(rs.getString("brief"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}