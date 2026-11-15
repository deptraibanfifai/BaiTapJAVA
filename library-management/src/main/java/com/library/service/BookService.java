package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookDAO bookDAO;

    public List<Book> findAll()                        { return bookDAO.findAll(); }
    public Book findById(int id)                       { return bookDAO.findById(id); }
    public List<Book> search(String keyword)           { return bookDAO.search(keyword); }
    public List<Book> findByCategory(int categoryId)   { return bookDAO.findByCategory(categoryId); }
    public List<Book> findNewest(int limit)            { return bookDAO.findNewest(limit); }
    public List<Book> findMostRented(int limit)        { return bookDAO.findMostRented(limit); }
    public int save(Book book)                         { return bookDAO.save(book); }
    public int update(Book book)                       { return bookDAO.update(book); }
    public int delete(int id)                          { return bookDAO.delete(id); }
    public int countAll()                              { return bookDAO.countAll(); }
    public int countAvailable()                        { return bookDAO.countAvailable(); }
}