package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    public void addBook_shouldThrowIllegalArgumentException_whenAnyFieldIsNull() {

        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(null, "Author", "Genre"));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("Name", null, "Genre"));
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook("Name", "Author", null));
    }

    @Test
    public void addBook_shouldCallRepositorySave_whenFieldsAreValid() {
        String name = "1984";
        String author = "George Orwell";
        String genre = "Dystopia";

        bookService.addBook(name, author, genre);

        BookDTO expectedDTO = new BookDTO(name, author, genre);
        verify(bookRepository).save(expectedDTO);
    }

    @Test
    public void getById_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        int id = 999;
        when(bookRepository.findById(id)).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> bookService.getById(id));
    }
}