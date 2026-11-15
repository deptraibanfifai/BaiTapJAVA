package com.library.dao;

import com.library.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final org.springframework.jdbc.core.RowMapper<Cart> cartMapper = (rs, rowNum) -> {
        Cart c = new Cart();
        c.setId(rs.getInt("id"));
        c.setUserId(rs.getInt("user_id"));
        c.setBookId(rs.getInt("book_id"));
        c.setBookTitle(rs.getString("title"));
        c.setBookAuthor(rs.getString("author"));
        c.setBookImage(rs.getString("image"));
        c.setBookPrice(rs.getDouble("price"));
        return c;
    };

    // Lấy giỏ hàng của user
    public List<Cart> findByUserId(int userId) {
        String sql = "SELECT c.*, b.title, b.author, b.image, b.price " +
                "FROM cart c JOIN books b ON c.book_id = b.id WHERE c.user_id=?";
        return jdbcTemplate.query(sql, cartMapper, userId);
    }

    // Đếm số sách trong giỏ
    public int countByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE user_id=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    // Kiểm tra sách đã có trong giỏ chưa
    public boolean exists(int userId, int bookId) {
        String sql = "SELECT COUNT(*) FROM cart WHERE user_id=? AND book_id=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId, bookId);
        return count > 0;
    }

    // Thêm vào giỏ
    public int add(int userId, int bookId) {
        String sql = "INSERT INTO cart (user_id, book_id) VALUES (?,?)";
        return jdbcTemplate.update(sql, userId, bookId);
    }

    // Xóa 1 item khỏi giỏ
    public int remove(int cartId) {
        String sql = "DELETE FROM cart WHERE id=?";
        return jdbcTemplate.update(sql, cartId);
    }

    // Xóa toàn bộ giỏ hàng của user
    public int clearByUserId(int userId) {
        String sql = "DELETE FROM cart WHERE user_id=?";
        return jdbcTemplate.update(sql, userId);
    }
    // Lấy các item được chọn
    public List<Cart> findByIds(List<Integer> ids) {

        String placeholders =
                String.join(",",
                        java.util.Collections.nCopies(
                                ids.size(),
                                "?"
                        ));

        String sql =
                "SELECT c.*, b.title, b.author, b.image, b.price " +
                        "FROM cart c " +
                        "JOIN books b ON c.book_id=b.id " +
                        "WHERE c.id IN (" + placeholders + ")";

        return jdbcTemplate.query(
                sql,
                cartMapper,
                ids.toArray()
        );
    }

    // Xóa các item đã chọn
    public void removeSelected(List<Integer> cartIds) {

        String placeholders =
                String.join(",",
                        java.util.Collections.nCopies(
                                cartIds.size(),
                                "?"
                        ));

        String sql =
                "DELETE FROM cart WHERE id IN (" +
                        placeholders +
                        ")";

        jdbcTemplate.update(
                sql,
                cartIds.toArray()
        );
    }
}