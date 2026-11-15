package com.library.dao;

import com.library.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Category> findAll() {
        String sql = "SELECT * FROM categories ORDER BY name";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category c = new Category();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            return c;
        });
    }

    public Category findById(int id) {
        String sql = "SELECT * FROM categories WHERE id=?";
        List<Category> list = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Category c = new Category();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            return c;
        }, id);
        return list.isEmpty() ? null : list.get(0);
    }
}