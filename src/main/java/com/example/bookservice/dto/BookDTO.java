package com.example.bookservice.dto;

public class BookDTO {
    private final String name;
    private final String author;
    private final String genre;

    public BookDTO(String name, String author, String genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return java.util.Objects.equals(name, bookDTO.name) &&
                java.util.Objects.equals(author, bookDTO.author) &&
                java.util.Objects.equals(genre, bookDTO.genre);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, author, genre);
    }
}
