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
    public ResponseEntity<List<BookDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer yearBefore,
            @RequestParam(required = false, defaultValue = "false") boolean sortByYearDesc) {

        // Ricerca per titolo O autore (se specificati)
        if (title != null || author != null) {
            return ResponseEntity.ok(bookService.searchBooksByTitleOrAuthor(title, author));
        }

        // Filtri specifici
        if (genre != null) {
            return ResponseEntity.ok(bookService.getBooksByGenre(genre));
        }

        if (yearBefore != null) {
            return ResponseEntity.ok(bookService.getBooksPublishedBeforeYear(yearBefore));
        }

        // Ordinamento
        if (sortByYearDesc) {
            return ResponseEntity.ok(bookService.getAllBooksSortedByYearDesc());
        }

        // Caso default: tutti i libri
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Manteniamo gli altri endpoint CRUD invariati
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
        if (book.id() != null) {
            return ResponseEntity.badRequest().body("Non includere campo id, ci pensa il database");
        }
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
        return deleted ?
                ResponseEntity.ok("Libro con id = " + id + " eliminato") :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> countByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.countBooksByAuthor(author));
    }
}