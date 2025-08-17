package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.repository.CollectionBookRepositoryImpl;

import java.util.*;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    private void log(String message) {
        System.out.println("[LOG] " + message);
    }

    /**
     * Method to add a new book to the library.
     *
     * @param name   - the name of the book
     * @param author - the author of the book
     * @param genre  - the genre of the book
     */
    public void addBook(String name, String author, String genre) {
        if (name == null || author == null || genre == null) {
            throw new IllegalArgumentException("Name, author and genre cannot be null");
        }

        BookDTO dto = new BookDTO(name, author, genre); // ID будет назначен автоматически
        bookRepository.save(dto);
        log("Added book: " + dto);
    }

    public void deleteBook(int id) {
        Book book = booksById.remove(id);
        if (book == null) {
            throw new NoSuchElementException("Book with ID " + id + " not found");
        }

        // Удаляем книгу из всех индексов
        booksByName.get(book.getName()).remove(book);
        booksByAuthor.get(book.getAuthor()).remove(book);
        //booksByGenre.get(book.getGenre()).remove(book);
        booksByGenre.remove(book.getGenre());

        // Добавляем ID в очередь свободных
        availableIds.add(id);
        log("Deleted book: " + book);
    }

    public Book getById(int id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            log("Book with ID " + id + " not found");
            throw new NoSuchElementException("Book with ID " + id + " not found"); // BookNotFoundException - нужно свою создать
        } else {
            log("Found book by ID: " + id);
        }
        return book;
    }

    public List<Book> findByName(String name) {
        List<Book> books = booksByName.getOrDefault(name, Collections.emptyList());
        log("Found " + books.size() + " books by name: " + name);
        return books;
    }

    public List<Book> findByAuthor(String author) {
        List<Book> books = booksByAuthor.getOrDefault(author, Collections.emptyList());
        log("Found " + books.size() + " books by author: " + author);
        return books;
    }

    public List<Book> findByGenre(String genre) {
        List<Book> books = booksByGenre.getOrDefault(genre, Collections.emptyList());
        log("Found " + books.size() + " books by genre: " + genre);
        return books;
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>(booksById.values());
        log("Search all books in the library. Total number of books= " + allBooks.size());
        return allBooks;
    }
}