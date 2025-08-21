package com.example.bookservice.service;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;

import java.util.List;


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

        BookDTO dto = new BookDTO(name, author, genre);
        bookRepository.save(dto);
        log("Added book: " + dto);
    }

    public void deleteBook(int id) {
        bookRepository.delete(id);
        log("Delete book with ID: " + id);
    }

    public Book getById(int id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            log("Book with ID " + id + " not found");
            throw new BookNotFoundException(id);
        }
        return book;
    }

    public List<Book> findByName(String name) {
        List<Book> books = bookRepository.findByName(name);
        log("Found " + books.size() + " books by name: " + name);
        return books;
    }

    public List<Book> findByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        log("Found " + books.size() + " books by author: " + author);
        return books;
    }

    public List<Book> findByGenre(String genre) {
        List<Book> books = bookRepository.findByGenre(genre);
        log("Found " + books.size() + " books by genre: " + genre);
        return books;
    }

    public List<Book> findAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        log("Search all books in the library. Total number of books= " + allBooks.size());
        return allBooks;
    }
}