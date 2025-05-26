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

    private final IBookService bookService;

    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        // Recupera tutti i libri disponibili
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Long id) {
        // Recupera un libro specifico per ID
        try {
            BookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (NoSuchElementException nseex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookDTO book) {
        // Crea un nuovo libro
        if (book.id() != null) {
            return ResponseEntity.badRequest().body("Non includere campo id, ci pensa il database");
        }
        BookDTO createdBook = bookService.createBook(book);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody BookDTO book) {
        // Aggiorna un libro esistente
        try {
            BookDTO updatedBook = bookService.updateBook(id, book);
            return ResponseEntity.ok(updatedBook);
        } catch (NoSuchElementException nseex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        // Elimina un libro esistente
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return ResponseEntity.ok("Libro con id = " + id + " eliminato con successo.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<BookDTO>> getByAuthor(@PathVariable String author) {
        // Recupera libri per autore specifico
        List<BookDTO> books = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<BookDTO>> getByGenre(@PathVariable String genre) {
        // Recupera libri per genere specifico
        List<BookDTO> books = bookService.getBooksByGenre(genre);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<BookDTO>> searchByTitle(@PathVariable String title) {
        // Cerca libri per titolo (ricerca parziale case-insensitive)
        List<BookDTO> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/year/before/{year}")
    public ResponseEntity<List<BookDTO>> getPublishedBeforeYear(@PathVariable int year) {
        // Recupera libri pubblicati prima di un anno specifico
        List<BookDTO> books = bookService.getBooksPublishedBeforeYear(year);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/count/author/{author}")
    public ResponseEntity<Integer> countByAuthor(@PathVariable String author) {
        // Conta i libri di un autore specifico
        int count = bookService.countBooksByAuthor(author);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/sorted/year-desc")
    public ResponseEntity<List<BookDTO>> getAllSortedByYearDesc() {
        // Recupera tutti i libri ordinati per anno decrescente
        List<BookDTO> books = bookService.getAllBooksSortedByYearDesc();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchByTitleOrAuthor(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author) {
        // Cerca libri per titolo OPPURE autore (parametri opzionali)
        List<BookDTO> books = bookService.searchBooksByTitleOrAuthor(title, author);
        return ResponseEntity.ok(books);
    }
}