package com.library.service;

import com.library.dao.CategoryDAO;
import com.library.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    public List<Category> findAll()      { return categoryDAO.findAll(); }
    public Category findById(int id)     { return categoryDAO.findById(id); }
}