package com.example.bookservice.repository;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.model.Book;

public interface BookRepository {

    Book findById(int id);
    void save(BookDTO dto);
}
