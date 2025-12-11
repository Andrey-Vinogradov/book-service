package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;


    @ParameterizedTest
    @MethodSource("provideInvalidBookArguments")
    public void addBook_shouldThrowIllegalArgumentException_whenAnyFieldIsNull(String name, String author, String genre) {
        assertThrows(IllegalArgumentException.class, () -> bookService.addBook(name, author, genre));
    }

    @Test
    public void addBook_shouldCallRepositorySave_whenFieldsAreValid() {
        String name = "1984";
        String author = "George Orwell";
        String genre = "Dystopia";
        BookDTO expectedDTO = new BookDTO(name, author, genre);

        bookService.addBook(name, author, genre);

        verify(bookRepository, times(1)).save(expectedDTO);
    }


    @Test
    public void getById_shouldThrowBookNotFoundException_whenBookDoesNotExist() {
        when(bookRepository.findById(999)).thenReturn(null);
        assertThrows(BookNotFoundException.class, () -> bookService.getById(999));
    }

    @Test
    public void getById_shouldReturnBook_whenBookExists() {
        Book expectedBook = new Book(1, "1984", "George Orwell", "Dystopia");
        when(bookRepository.findById(1)).thenReturn(expectedBook);

        Book result = bookService.getById(1);

        assertEquals(expectedBook, result);
    }

    @Test
    public void deleteBook_shouldCallRepositoryDelete() {
        bookService.deleteBook(5);

        verify(bookRepository, times(1)).delete(5);
    }

    @Test
    public void findAllByName_shouldReturnBooks_whenBooksExist() {
        List<Book> expected = List.of(
                new Book(1, "1984", "George Orwell", "Dystopia"),
                new Book(2, "1984: The Sequel", "George Orwell", "Dystopia")
        );
        when(bookRepository.findAllByName("1984")).thenReturn(expected);

        List<Book> result = bookService.findByName("1984");

        assertEquals(expected, result);
    }

    @Test
    public void findAllByName_shouldReturnEmptyList_whenNoBooksFound() {
        when(bookRepository.findAllByName("Unknown")).thenReturn(List.of());
        assertEquals(List.of(), bookService.findByName("Unknown"));
    }

    @Test
    public void findAllByAuthor_shouldReturnBooks() {
        List<Book> expected = List.of(new Book(1, "Animal Farm", "George Orwell", "Satire"));
        when(bookRepository.findAllByAuthor("George Orwell")).thenReturn(expected);

        assertEquals(expected, bookService.findByAuthor("George Orwell"));
    }

    @Test
    public void findAllByGenre_shouldReturnBooks() {
        List<Book> expected = List.of(new Book(1, "Dune", "Frank Herbert", "Sci-Fi"));
        when(bookRepository.findAllByGenre("Sci-Fi")).thenReturn(expected);

        assertEquals(expected, bookService.findByGenre("Sci-Fi"));
    }

    @Test
    public void findAllBooks_shouldReturnAllBooks() {
        List<Book> expected = List.of(
                new Book(1, "Book1", "Author1", "Genre1"),
                new Book(2, "Book2", "Author2", "Genre2")
        );
        when(bookRepository.findAll()).thenReturn(expected);

        assertEquals(expected, bookService.findAllBooks());
    }

    private static Stream<Arguments> provideInvalidBookArguments() {
        return Stream.of(
                Arguments.of(null, "Author", "Genre"),
                Arguments.of("Name", null, "Genre"),
                Arguments.of("Name", "Author", null)
        );
    }
}