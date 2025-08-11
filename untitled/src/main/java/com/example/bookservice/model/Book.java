package com.example.bookservice.model;

public class Book {
    private final int id;
    private final String name;
    private final String author;
    private final String genre;

    public Book(int id, String name, String author, String genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }


    public int getId() {
        return id;
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
        return "Book{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}