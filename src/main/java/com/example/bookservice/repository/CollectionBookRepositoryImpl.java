package com.example.bookservice.repository;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.model.Book;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

//import java.util.*;

public class CollectionBookRepositoryImpl implements BookRepository {
    private final Map<Integer, Book> booksById = new HashMap<>();
    private final Map<String, List<Book>> booksByName = new ConcurrentHashMap<>();
    private final Map<String, List<Book>> booksByAuthor = new ConcurrentHashMap<>();
    private final Map<String, List<Book>> booksByGenre = new ConcurrentHashMap<>();
    private final Queue<Integer> availableIds = new PriorityQueue<>();
    private int nextId = 1;

    /**
     * Method to get the next available ID for a new book.
     *
     * @return the next available ID
     */
    private int getNextAvailableId() {
        if (!availableIds.isEmpty()) {
            return availableIds.poll();
        }
        return nextId++;
    }

    @Override
    public Book findById(int id) {
        return booksById.get(id);
    }

    @Override
    public void save(BookDTO dto) {
        int id = getNextAvailableId();
        Book bookToSave = new Book(id, dto.getName(), dto.getAuthor(), dto.getGenre());

        booksById.put(id, bookToSave);
        booksByName.computeIfAbsent(bookToSave.getName(), k -> new ArrayList<>()).add(bookToSave);
        booksByAuthor.computeIfAbsent(bookToSave.getAuthor(), k -> new ArrayList<>()).add(bookToSave);
        booksByGenre.computeIfAbsent(bookToSave.getGenre(), k -> new ArrayList<>()).add(bookToSave);
    }
    @Override
    public void delete(int id){
        Book book = booksById.remove(id);
        if (book == null){
            throw new BookNotFoundException(id);
        }
        booksByName.remove(book.getName());
        booksByAuthor.remove(book.getAuthor());
        booksByGenre.remove(book.getGenre());

        availableIds.add(id);
    }
    @Override
    public List<Book> findByName(String name){
        return booksByName.getOrDefault(name, Collections.emptyList());
    }
    @Override
    public List<Book> findByAuthor(String author){
        return booksByAuthor.getOrDefault(author, Collections.emptyList());
    }
    @Override
    public List<Book> findByGenre(String genre){
        return booksByGenre.getOrDefault(genre, Collections.emptyList());
    }
    @Override
    public List<Book> findAll(){
        return new ArrayList<>(booksById.values());
    }

}
