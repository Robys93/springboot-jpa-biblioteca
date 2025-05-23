package com.giuseppe.biblioteca.controller;

import com.giuseppe.biblioteca.model.BookDTO;
import com.giuseppe.biblioteca.service.IBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookDTO> getAll() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.getBookById(id));
        } catch (NoSuchElementException nseex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDTO book) {
        if (book.id() != null)
            return ResponseEntity.badRequest().body("Non includere campo id, ci pensa il database");

        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody BookDTO book) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, book));
        } catch (NoSuchElementException nseex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.ok("Libro con id =" + id + " eliminato con successo.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/author/{author}")
    public List<BookDTO> getByAuthor(@PathVariable String author) {
        return bookService.findByAuthor(author);
    }

    @GetMapping("/genre/{genre}")
    public List<BookDTO> getByGenre(@PathVariable String genre) {
        return bookService.findByGenre(genre);
    }

    @GetMapping("/search/title/{title}")
    public List<BookDTO> searchByTitle(@PathVariable String title) {
        return bookService.findByTitleContaining(title);
    }

    @GetMapping("/year/before/{year}")
    public List<BookDTO> getByYearBefore(@PathVariable int year) {
        return bookService.findByPublicationYearBefore(year);
    }

    @GetMapping("/count/author/{author}")
    public int countByAuthor(@PathVariable String author) {
        return bookService.countBooksByAuthor(author);
    }

    @GetMapping("/sorted/year-desc")
    public List<BookDTO> getAllSortedByYearDesc() {
        return bookService.findAllOrderByPublicationYearDesc();
    }

    @GetMapping("/search/{keyword}")
    public List<BookDTO> searchByTitleOrAuthor(@PathVariable String keyword) {
        return bookService.findByTitleOrAuthor(keyword);
    }
}