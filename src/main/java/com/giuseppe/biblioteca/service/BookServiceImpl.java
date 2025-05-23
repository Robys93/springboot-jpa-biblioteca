package com.giuseppe.biblioteca.service;

import com.giuseppe.biblioteca.model.Book;
import com.giuseppe.biblioteca.model.BookDTO;
import com.giuseppe.biblioteca.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // ... metodi esistenti ...

    @Override
    public List<BookDTO> getAllBooks() {
        return List.of();
    }

    @Override
    public BookDTO getBookById(Long id) {
        return null;
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        return null;
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        return null;
    }

    @Override
    public boolean deleteBook(Long id) {
        return false;
    }

    @Override
    public List<BookDTO> findByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByGenre(String genre) {
        return bookRepository.findByGenre(genre).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByPublicationYearBefore(int year) {
        return bookRepository.findByPubblicationYearLessThan(year).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public int countBooksByAuthor(String author) {
        return bookRepository.countByAuthor(author);
    }

    @Override
    public List<BookDTO> findAllOrderByPublicationYearDesc() {
        return bookRepository.findAllByOrderByPubblicationYearDesc().stream()
                .map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByTitleOrAuthor(String keyword) {
        return bookRepository.findByTitleOrAuthor(keyword).stream()
                .map(this::toDTO).collect(Collectors.toList());
    }
}