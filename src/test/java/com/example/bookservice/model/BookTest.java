package com.example.bookservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void shouldCreateBookWithCorrectFields() {
        // Arrange
        int id = 1;
        String name = "1984";
        String author = "George Orwell";
        String genre = "Dystopia";

        Book book = new Book(id, name, author, genre);

        assertEquals(id, book.getId());
        assertEquals(name, book.getName());
        assertEquals(author, book.getAuthor());
        assertEquals(genre, book.getGenre());
    }

    @Test
    public void shouldReturnCorrectToString() {

        Book book = new Book(1, "1984", "George Orwell", "Dystopia");

        String result = book.toString();

        String expected = "Book{id='1', name='1984', author='George Orwell', genre='Dystopia'}";
        assertEquals(expected, result);
    }
}