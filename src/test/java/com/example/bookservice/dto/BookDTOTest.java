package com.example.bookservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookDTOTest {

    @Test
    public void shouldCreateBookDTOWithCorrectFields() {
        String name = "The Silmarillion";
        String author = "J. R. R. Tolkien";
        String genre = "Fantasy";

        BookDTO bookDTO = new BookDTO(name, author, genre);

        assertEquals(name, bookDTO.name());
        assertEquals(author, bookDTO.author());
        assertEquals(genre, bookDTO.genre());
    }

    @Test
    public void shouldReturnCorrectToString() {
        BookDTO bookDTO = new BookDTO("The Silmarillion", "J. R. R. Tolkien", "Fantasy");

        String result = bookDTO.toString();

        String expected = "BookDTO[name=The Silmarillion, author=J. R. R. Tolkien, genre=Fantasy]";
        assertEquals(expected, result);
    }
}