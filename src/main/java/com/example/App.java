package com.example;

import com.example.model.Book;
import com.example.repository.BookRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class App {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(App.class, args);
        var repo = context.getBean(BookRepository.class);

        List<Book> books = List.of(
                new Book (null, "book1", "author1", 13.55),
                new Book (null, "book2", "author2", 22.44),
                new Book (null, "book3", "author3", 8.99),
                new Book (null, "book4", "author4", 34.50)
        );

        repo.saveAll(books);








    }

}
