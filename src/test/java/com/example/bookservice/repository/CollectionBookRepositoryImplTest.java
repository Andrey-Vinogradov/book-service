package com.example.bookservice.repository;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CollectionBookRepositoryImplTest {

    private CollectionBookRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new CollectionBookRepositoryImpl();
    }

    @Test
    public void save_shouldAddBookToRepository() {
        BookDTO dto = new BookDTO("Test Book", "Test Author", "Test Genre");

        repository.save(dto);

        List<Book> allBooks = repository.findAll();
        assertEquals(1, allBooks.size(), "Repository should contain 1 book after save");

        Book savedBook = allBooks.getFirst();
        assertEquals(dto.getName(), savedBook.getName());
        assertEquals(dto.getAuthor(), savedBook.getAuthor());
        assertEquals(dto.getGenre(), savedBook.getGenre());
        assertTrue(savedBook.getId() > 0, "Saved book should have a positive ID");
    }

    @Test
    public void findById_shouldReturnBook_whenBookExists() {
        BookDTO dto = new BookDTO("Find Me", "Author A", "Genre B");
        repository.save(dto);
        Book expectedBook = new Book(1, "Find Me", "Author A", "Genre B");

        Book foundBook = repository.findById(1);

        assertNotNull(foundBook, "Book should be found by ID 1");
        assertEquals(expectedBook, foundBook);
    }

    @Test
    public void findById_shouldReturnNull_whenBookDoesNotExist() {

        Book foundBook = repository.findById(999);

        assertNull(foundBook, "Book should not be found for non-existent ID");
    }

    @Test
    public void delete_shouldRemoveBookAndThrowExceptionOnSecondDelete() {
        BookDTO dto = new BookDTO("To Be Deleted", "Author B", "Genre C");
        repository.save(dto);
        Book bookToDelete = repository.findById(1);
        assertNotNull(bookToDelete, "Book should exist before deletion");

        repository.delete(1);

        assertNull(repository.findById(1), "Book should not be found after deletion");

        List<Book> allBooksAfterDelete = repository.findAll();
        assertEquals(0, allBooksAfterDelete.size(), "Repository should be empty after deleting the only book");

        assertThrows(BookNotFoundException.class, () -> repository.delete(1));
    }
    @Test
    public void findByName_shouldReturnBooks_whenBooksExist() {
        // Arrange: Добавляем книги с разными именами
        BookDTO dto1 = new BookDTO("Book A", "Author X", "Genre 1");
        BookDTO dto2 = new BookDTO("Book B", "Author Y", "Genre 2");
        BookDTO dto3 = new BookDTO("Book A", "Author Z", "Genre 3");
        repository.save(dto1);
        repository.save(dto2);
        repository.save(dto3);

        List<Book> foundBooks = repository.findByName("Book A");

        assertEquals(2, foundBooks.size(), "Should find 2 books with name 'Book A'");
        assertTrue(foundBooks.stream().allMatch(book -> "Book A".equals(book.getName())),
                "All found books should have the name 'Book A'");

        assertTrue(foundBooks.contains(new Book(1, "Book A", "Author X", "Genre 1")));
        assertTrue(foundBooks.contains(new Book(3, "Book A", "Author Z", "Genre 3")));
    }

    @Test
    public void findByName_shouldReturnEmptyList_whenNoBooksFound() {
        BookDTO dto1 = new BookDTO("Book A", "Author X", "Genre 1");
        repository.save(dto1);

        List<Book> foundBooks = repository.findByName("Non-existent Book");

        assertTrue(foundBooks.isEmpty(), "Should return an empty list for non-existent name");
        assertEquals(List.of(), foundBooks, "Should return an empty list for non-existent name");
    }

    @Test
    public void findByName_shouldReturnEmptyList_whenNameIsNull() {
        List<Book> foundBooks = repository.findByName(null);

        assertTrue(foundBooks.isEmpty(), "Should return an empty list for null name");
        assertEquals(List.of(), foundBooks, "Should return an empty list for null name");
    }

    // ... другие тесты для findByName, findByAuthor, findByGenre, findAll
}
