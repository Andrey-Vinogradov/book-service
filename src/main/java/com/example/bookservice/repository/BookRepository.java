package com.example.bookservice.repository;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.model.Book;

import java.util.List;

public interface BookRepository {

    Book findById(int id);

    void save(BookDTO dto);

    void delete(int id);

    List<Book> findAllByName(String name);

    List<Book> findAllByAuthor(String author);

    List<Book> findAllByGenre(String genre);

    List<Book> findAll();
}
