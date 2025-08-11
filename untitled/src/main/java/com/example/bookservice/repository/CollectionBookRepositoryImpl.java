package com.example.bookservice.repository;

import com.example.bookservice.dto.BookDTO;
import com.example.bookservice.model.Book;

import java.util.*;

public class CollectionBookRepositoryImpl implements BookRepository {
    private final Map<Integer, Book> booksById = new HashMap<>();
    private final Map<String, List<Book>> booksByName = new HashMap<>();
    private final Map<String, List<Book>> booksByAuthor = new HashMap<>();
    private final Map<String, List<Book>> booksByGenre = new HashMap<>();
    private final Queue<Integer> availableIds = new PriorityQueue<>();
    private int nextId = 1;

    /**
     * Method to get the next available ID for a new book.
     *
     * @return the next available ID
     */
    private int getNextAvailableId() {
        // Если есть свободные ID в очереди, берем наименьший
        if (!availableIds.isEmpty()) {
            return availableIds.poll();
        }
        // Иначе берем следующий новый ID
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

}
