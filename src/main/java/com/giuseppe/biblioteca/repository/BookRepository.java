package com.giuseppe.biblioteca.repository;

import com.giuseppe.biblioteca.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthor(String author);

    List<Book> findByGenre(String genre);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAnnoLessThan(int year);

    int countByAuthor(String author);

    List<Book> findAllByOrderByAnnoDesc();

    List<Book> findByTitleOrAuthor(String title, String author);
}
