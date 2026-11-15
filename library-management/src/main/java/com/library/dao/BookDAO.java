package com.library.dao;

import com.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final org.springframework.jdbc.core.RowMapper<Book> bookMapper = (rs, rowNum) -> {
        Book b = new Book();
        b.setId(rs.getInt("id"));
        b.setTitle(rs.getString("title"));
        b.setAuthor(rs.getString("author"));
        b.setCategoryId(rs.getInt("category_id"));
        b.setDescription(rs.getString("description"));
        b.setPrice(rs.getDouble("price"));
        b.setQuantity(rs.getInt("quantity"));
        b.setAvailableQuantity(rs.getInt("available_quantity"));
        b.setImage(rs.getString("image"));
        b.setPublishYear(rs.getInt("publish_year"));
        try { b.setCategoryName(rs.getString("category_name")); } catch (Exception ignored) {}
        return b;
    };

    // Lấy tất cả sách kèm tên category
    public List<Book> findAll() {
        String sql = "SELECT b.*, c.name as category_name FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "ORDER BY b.created_at DESC";
        return jdbcTemplate.query(sql, bookMapper);
    }

    // Tìm theo id
    public Book findById(int id) {
        String sql = "SELECT b.*, c.name as category_name FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.id WHERE b.id=?";
        List<Book> list = jdbcTemplate.query(sql, bookMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    // Tìm kiếm theo từ khóa
    public List<Book> search(String keyword) {
        String sql = "SELECT b.*, c.name as category_name FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE b.title LIKE ? OR b.author LIKE ? OR c.name LIKE ? " +
                "ORDER BY b.created_at DESC";
        String kw = "%" + keyword + "%";
        return jdbcTemplate.query(sql, bookMapper, kw, kw, kw);
    }

    // Lọc theo category
    public List<Book> findByCategory(int categoryId) {
        String sql = "SELECT b.*, c.name as category_name FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE b.category_id=? ORDER BY b.created_at DESC";
        return jdbcTemplate.query(sql, bookMapper, categoryId);
    }

    // Sách mới nhất
    public List<Book> findNewest(int limit) {
        String sql = "SELECT b.*, c.name as category_name FROM books b " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "ORDER BY b.created_at DESC LIMIT ?";
        return jdbcTemplate.query(sql, bookMapper, limit);
    }

    // Sách được thuê nhiều nhất
    public List<Book> findMostRented(int limit) {
        String sql = "SELECT b.*, c.name as category_name, COUNT(rd.book_id) as rent_count " +
                "FROM books b LEFT JOIN categories c ON b.category_id = c.id " +
                "LEFT JOIN rental_details rd ON b.id = rd.book_id " +
                "GROUP BY b.id ORDER BY rent_count DESC LIMIT ?";
        return jdbcTemplate.query(sql, bookMapper, limit);
    }

    // Thêm sách
    public int save(Book book) {
        String sql = "INSERT INTO books (title, author, category_id, description, price, " +
                "quantity, available_quantity, image, publish_year) VALUES (?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql,
                book.getTitle(), book.getAuthor(), book.getCategoryId(),
                book.getDescription(), book.getPrice(), book.getQuantity(),
                book.getQuantity(), book.getImage(), book.getPublishYear());
    }

    // Cập nhật sách - Sửa lỗi logic: Tính toán lại số lượng còn lại
    public int update(Book book) {
        // Lấy thông tin sách cũ để tính chênh lệch số lượng
        Book oldBook = findById(book.getId());
        int diff = book.getQuantity() - oldBook.getQuantity();
        int newAvailable = oldBook.getAvailableQuantity() + diff;
        
        // Đảm bảo available không âm
        if (newAvailable < 0) newAvailable = 0;

        String sql = "UPDATE books SET title=?, author=?, category_id=?, description=?, " +
                "price=?, quantity=?, available_quantity=?, image=?, publish_year=? WHERE id=?";
        return jdbcTemplate.update(sql,
                book.getTitle(), book.getAuthor(), book.getCategoryId(),
                book.getDescription(), book.getPrice(), book.getQuantity(),
                newAvailable, book.getImage(), book.getPublishYear(), book.getId());
    }

    // Xóa sách
    public int delete(int id) {
        String sql = "DELETE FROM books WHERE id=?";
        return jdbcTemplate.update(sql, id);
    }

    // Cập nhật số lượng khi thuê
    public int decreaseQuantity(int bookId) {
        String sql = "UPDATE books SET available_quantity = available_quantity - 1 " +
                "WHERE id=? AND available_quantity > 0";
        return jdbcTemplate.update(sql, bookId);
    }

    // Cập nhật số lượng khi trả
    public int increaseQuantity(int bookId) {
        String sql = "UPDATE books SET available_quantity = available_quantity + 1 WHERE id=?";
        return jdbcTemplate.update(sql, bookId);
    }

    // Đếm tổng sách
    public int countAll() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM books", Integer.class);
    }

    // Đếm sách còn available
    public int countAvailable() {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT SUM(available_quantity) FROM books", Integer.class);
        return result != null ? result : 0;
    }
}