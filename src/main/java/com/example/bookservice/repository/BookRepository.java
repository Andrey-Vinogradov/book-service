package com.example.bookservice.repository;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.model.Book;

import java.util.List;

public interface BookRepository {

    Book findById(int id);

    void save(BookDTO dto);

    void delete(int id);

    List<Book> findByName(String name);

    List<Book> findByAuthor(String author);

    List<Book> findByGenre(String genre);

    List<Book> findAll();
}
