package com.example.bookservice;

import java.util.*;

public class BookService {
    private final Map<Integer, Book> booksById = new HashMap<>();
    private final Map<String, List<Book>> booksByName = new HashMap<>();
    private final Map<String, List<Book>> booksByAuthor = new HashMap<>();
    private final Map<String, List<Book>> booksByGenre = new HashMap<>();
    private final Queue<Integer> availableIds = new PriorityQueue<>();
    private int nextId = 1;

    private void log(String message) {
        System.out.println("[LOG] " + message);
    }

    private int getNextAvailableId() {
        // Если есть свободные ID в очереди, берем наименьший
        if (!availableIds.isEmpty()) {
            return availableIds.poll();
        }
        // Иначе берем следующий новый ID
        return nextId++;
    }

    public void addBook(String name, String author, String genre) {
        if (name == null || author == null || genre == null) {
            throw new IllegalArgumentException("Name, author and genre cannot be null");
        }

        int id = getNextAvailableId();
        Book book = new Book(id, name, author, genre);

        booksById.put(id, book);
        booksByName.computeIfAbsent(name, k -> new ArrayList<>()).add(book);
        booksByAuthor.computeIfAbsent(author, k -> new ArrayList<>()).add(book);
        booksByGenre.computeIfAbsent(genre, k -> new ArrayList<>()).add(book);

        log("Added book: " + book);
    }

    public void deleteBook(int id) {
        Book book = booksById.remove(id);
        if (book == null) {
            throw new NoSuchElementException("Book with ID " + id + " not found");
        }

        // Удаляем книгу из всех индексов
        booksByName.get(book.getName()).remove(book);
        booksByAuthor.get(book.getAuthor()).remove(book);
        booksByGenre.get(book.getGenre()).remove(book);

        // Добавляем ID в очередь свободных
        availableIds.add(id);
        log("Deleted book: " + book);
    }

    public Book findById(int id) {
        Book book = booksById.get(id);
        if (book == null) {
            log("Book with ID " + id + " not found");
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