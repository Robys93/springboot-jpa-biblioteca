package com.giuseppe.biblioteca.service;

import com.giuseppe.biblioteca.model.BookDTO;

import java.util.List;

public interface IBookService {
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    BookDTO createBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    boolean deleteBook(Long id);

    // Nuovi metodi aggiunti
    List<BookDTO> findByAuthor(String author);
    List<BookDTO> findByGenre(String genre);
    List<BookDTO> findByTitleContaining(String title);
    List<BookDTO> findByPublicationYearBefore(int year);
    int countBooksByAuthor(String author);
    List<BookDTO> findAllOrderByPublicationYearDesc();
    List<BookDTO> findByTitleOrAuthor(String keyword);
}