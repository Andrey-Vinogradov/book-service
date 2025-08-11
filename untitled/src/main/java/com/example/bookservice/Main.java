package com.example.bookservice;

import com.example.bookservice.repository.CollectionBookRepositoryImpl;
import com.example.bookservice.service.BookService;

public class Main {
    public static void main(String[] args) {
        BookService service = new BookService(new CollectionBookRepositoryImpl());

        // Добавляем книги (теперь без указания ID)
        service.addBook("Clean Code", "Robert Martin", "textbooks");
        service.addBook("Effective Java", "Joshua Bloch", "textbooks");
        service.addBook("Not Effective Java", "Joshua Bloch", "fantasy");
        service.addBook("The best Java", "Joshua Bloch", "fantasy");
        service.addBook("The best of the Java", "Joshua Mainer", "fantasy");

        System.out.println(service.getById(1));
        System.out.println("*****");
        System.out.println(service.findByName("Effective Java"));
        System.out.println("*****");
        System.out.println(service.findByAuthor("Joshua Bloch"));
        System.out.println("*****");
        System.out.println(service.findByGenre("textbooks"));
        System.out.println("*****");
        System.out.println(service.getAllBooks());
        System.out.println("*****");

        // Удаляем книгу с ID 3
        service.deleteBook(3);
        System.out.println("*****");
        System.out.println(service.getAllBooks());

        // Добавляем новую книгу - она должна получить ID 3 (освобожденный)
        service.addBook("Next book of the Java", "Kevin Mainer", "fantasy");
        System.out.println(service.getAllBooks());
    }
}