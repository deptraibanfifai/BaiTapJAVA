package com.library.dao;

import com.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper tái sử dụng
    private RowMapper<User> userMapper = (rs, rowNum) -> {
        User u = new User();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setFullName(rs.getString("full_name"));
        u.setPhone(rs.getString("phone"));
        u.setRole(rs.getString("role"));
        u.setStatus(rs.getString("status"));
        u.setCreatedAt(rs.getString("created_at"));
        return u;
    };

    // Tìm user theo username
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> list = jdbcTemplate.query(sql, userMapper, username);
        return list.isEmpty() ? null : list.get(0);
    }

    // Tìm user theo email
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> list = jdbcTemplate.query(sql, userMapper, email);
        return list.isEmpty() ? null : list.get(0);
    }

    // Tìm user theo id
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> list = jdbcTemplate.query(sql, userMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    // Lấy tất cả users
    public List<User> findAll() {
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, userMapper);
    }

    // Đăng ký user mới
    public int save(User user) {
        String sql = "INSERT INTO users (username, email, password, full_name, phone, role, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                user.getUsername(), user.getEmail(), user.getPassword(),
                user.getFullName(), user.getPhone(),
                user.getRole() != null ? user.getRole() : "USER",
                user.getStatus() != null ? user.getStatus() : "ACTIVE");
    }

    // Cập nhật thông tin
    public int update(User user) {
        String sql = "UPDATE users SET full_name=?, phone=?, email=? WHERE id=?";
        return jdbcTemplate.update(sql,
                user.getFullName(), user.getPhone(), user.getEmail(), user.getId());
    }

    // Đổi mật khẩu
    public int updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password=? WHERE id=?";
        return jdbcTemplate.update(sql, newPassword, userId);
    }

    // Khóa / mở khóa tài khoản
    public int updateStatus(int userId, String status) {
        String sql = "UPDATE users SET status=? WHERE id=?";
        return jdbcTemplate.update(sql, status, userId);
    }

    // Xóa user
    public int delete(int userId) {
        String sql = "DELETE FROM users WHERE id=?";
        return jdbcTemplate.update(sql, userId);
    }

    // Đếm tổng users
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM users WHERE role='USER'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}