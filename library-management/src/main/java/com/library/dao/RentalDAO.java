package com.library.dao;

import com.library.model.Rental;
import com.library.model.RentalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class RentalDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final org.springframework.jdbc.core.RowMapper<Rental> rentalMapper = (rs, rowNum) -> {

        Rental r = new Rental();

        r.setId(rs.getInt("id"));
        r.setUserId(rs.getInt("user_id"));
        r.setRentalDate(rs.getString("rental_date"));
        r.setDueDate(rs.getString("due_date"));
        r.setReturnDate(rs.getString("return_date"));
        r.setStatus(rs.getString("status"));
        r.setCreatedAt(rs.getString("created_at"));

        try {
            r.setUsername(rs.getString("username"));
        } catch (Exception ignored) {}

        try {
            r.setRentalFee(rs.getDouble("rental_fee"));
            r.setLateFee(rs.getDouble("late_fee"));
            r.setTotalAmount(rs.getDouble("total_amount"));
            try {
                r.setPaymentStatus(
                        rs.getString("payment_status")
                );
            } catch (Exception ignored) {}
        } catch (Exception ignored) {}

        return r;
    };

    // =====================================================
    // TẠO PHIẾU THUÊ
    // =====================================================

    public int save(Rental rental) {

        String sql =
                "INSERT INTO rentals " +
                        "(user_id, rental_date, due_date, status, rental_fee, late_fee, total_amount, payment_status) " +
                        "VALUES (?,?,?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {

            PreparedStatement ps =
                    con.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setInt(1, rental.getUserId());
            ps.setString(2, rental.getRentalDate());
            ps.setString(3, rental.getDueDate());
            ps.setString(4, "PENDING");

            ps.setDouble(5, rental.getRentalFee());
            ps.setDouble(6, rental.getLateFee());
            ps.setDouble(7, rental.getTotalAmount());
            ps.setString(8, "PAID");


            return ps;

        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    // =====================================================
    // CHI TIẾT THUÊ
    // =====================================================

    public void saveDetail(int rentalId, int bookId) {

        String sql =
                "INSERT INTO rental_details(rental_id,book_id) VALUES(?,?)";

        jdbcTemplate.update(sql, rentalId, bookId);
    }

    public List<RentalDetail> findDetailsByRentalId(int rentalId) {

        String sql =
                "SELECT rd.*, " +
                        "b.title AS book_title, " +
                        "b.author AS book_author, " +
                        "b.image AS book_image " +
                        "FROM rental_details rd " +
                        "JOIN books b ON rd.book_id=b.id " +
                        "WHERE rd.rental_id=?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            RentalDetail rd = new RentalDetail();

            rd.setId(rs.getInt("id"));
            rd.setRentalId(rs.getInt("rental_id"));
            rd.setBookId(rs.getInt("book_id"));

            rd.setBookTitle(rs.getString("book_title"));
            rd.setBookAuthor(rs.getString("book_author"));
            rd.setBookImage(rs.getString("book_image"));

            return rd;

        }, rentalId);
    }

    // =====================================================
    // TRUY VẤN RENTAL
    // =====================================================

    public List<Rental> findAll() {

        String sql =
                "SELECT r.*,u.username " +
                        "FROM rentals r " +
                        "JOIN users u ON r.user_id=u.id " +
                        "ORDER BY r.created_at DESC";

        return jdbcTemplate.query(sql, rentalMapper);
    }

    public Rental findById(int id) {

        String sql =
                "SELECT r.*,u.username " +
                        "FROM rentals r " +
                        "JOIN users u ON r.user_id=u.id " +
                        "WHERE r.id=?";

        List<Rental> list =
                jdbcTemplate.query(sql, rentalMapper, id);

        return list.isEmpty() ? null : list.get(0);
    }

    public List<Rental> findByUserId(int userId) {

        String sql =
                "SELECT * FROM rentals " +
                        "WHERE user_id=? " +
                        "ORDER BY created_at DESC";

        return jdbcTemplate.query(sql, rentalMapper, userId);
    }

    public List<Rental> findRecent(int limit) {

        String sql =
                "SELECT r.*,u.username " +
                        "FROM rentals r " +
                        "JOIN users u ON r.user_id=u.id " +
                        "ORDER BY r.created_at DESC " +
                        "LIMIT ?";

        return jdbcTemplate.query(sql, rentalMapper, limit);
    }
    public int updateLateFee(
            int rentalId,
            double lateFee
    ){

        String sql =
                "UPDATE rentals " +
                        "SET late_fee=?, " +
                        "total_amount=rental_fee+? " +
                        "WHERE id=?";

        return jdbcTemplate.update(
                sql,
                lateFee,
                lateFee,
                rentalId
        );
    }

    // =====================================================
    // CẬP NHẬT TRẠNG THÁI
    // =====================================================

    public int updateStatus(int rentalId, String status) {

        String sql =
                "UPDATE rentals SET status=? WHERE id=?";

        return jdbcTemplate.update(sql, status, rentalId);
    }

    public int confirmReturn(int rentalId) {

        String sql =
                "UPDATE rentals " +
                        "SET status='RETURNED', return_date=CURDATE() " +
                        "WHERE id=?";

        return jdbcTemplate.update(sql, rentalId);
    }

    // =====================================================
    // DASHBOARD
    // =====================================================

    public int countAll() {

        String sql =
                "SELECT COUNT(*) FROM rentals";

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class
        );
    }

    public int countRentingBooks() {

        String sql =
                "SELECT COUNT(rd.id) " +
                        "FROM rental_details rd " +
                        "JOIN rentals r ON rd.rental_id=r.id " +
                        "WHERE r.status='RENTING'";

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class
        );
    }

    public int countOverdueRentals() {

        String sql =
                "SELECT COUNT(*) " +
                        "FROM rentals " +
                        "WHERE status='OVERDUE'";

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class
        );
    }

    // =====================================================
    // DOANH THU
    // =====================================================

    public double revenueCurrentMonth() {

        String sql =
                "SELECT IFNULL(SUM(total_amount),0) " +
                        "FROM rentals " +
                        "WHERE MONTH(rental_date)=MONTH(CURDATE()) " +
                        "AND YEAR(rental_date)=YEAR(CURDATE())";

        return jdbcTemplate.queryForObject(
                sql,
                Double.class
        );
    }

    public double revenueCurrentYear() {

        String sql =
                "SELECT IFNULL(SUM(total_amount),0) " +
                        "FROM rentals " +
                        "WHERE YEAR(rental_date)=YEAR(CURDATE())";

        return jdbcTemplate.queryForObject(
                sql,
                Double.class
        );
    }

    public List<Double> getRevenueByMonth() {

        String sql =
                "SELECT MONTH(rental_date) m, " +
                        "SUM(total_amount) revenue " +
                        "FROM rentals " +
                        "GROUP BY MONTH(rental_date)";

        List<Double> result =
                new ArrayList<>(Collections.nCopies(12, 0.0));

        jdbcTemplate.query(sql, rs -> {

            int month = rs.getInt("m");

            result.set(
                    month - 1,
                    rs.getDouble("revenue")
            );
        });

        return result;
    }
    // cập nhật thanh toán
    public int updatePaymentStatus(
            int rentalId,
            String status
    ) {

        String sql =
                "UPDATE rentals " +
                        "SET payment_status=? " +
                        "WHERE id=?";

        return jdbcTemplate.update(
                sql,
                status,
                rentalId
        );
    }
}