package com.giuseppe.biblioteca.service;

import com.giuseppe.biblioteca.model.Book;
import com.giuseppe.biblioteca.model.BookDTO;
import com.giuseppe.biblioteca.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        // Recupera tutti i libri dal repository e li converte in DTO
        return bookRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        // Cerca un libro per ID e lo converte in DTO, solleva eccezione se non trovato
        return bookRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow();
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        // Converte il DTO in entità, lo salva nel repository e restituisce il DTO risultante
        Book bookEntity = toEntity(bookDTO);
        Book savedBook = bookRepository.save(bookEntity);
        return toDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        // Trova il libro esistente, aggiorna i campi e salva le modifiche
        Book existingBook = bookRepository.findById(id)
                .orElseThrow();

        existingBook.setTitle(bookDTO.title());
        existingBook.setAuthor(bookDTO.author());
        existingBook.setAnno(bookDTO.year());
        existingBook.setGenre(bookDTO.genre());

        Book updatedBook = bookRepository.save(existingBook);
        return toDTO(updatedBook);
    }

    @Override
    public boolean deleteBook(Long id) {
        // Elimina un libro se esiste, restituisce true se eliminato, false altrimenti
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<BookDTO> getBooksByAuthor(String author) {
        // Recupera tutti i libri di un autore specifico
        return bookRepository.findByAuthor(author).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksByGenre(String genre) {
        // Recupera tutti i libri di un genere specifico
        return bookRepository.findByGenre(genre).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooksByTitle(String title) {
        // Cerca libri per titolo (case insensitive, ricerca parziale)
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> getBooksPublishedBeforeYear(int year) {
        // Recupera libri pubblicati prima di un anno specifico
        return bookRepository.findByAnnoLessThan(year).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public int countBooksByAuthor(String author) {
        // Conta il numero di libri di un autore specifico
        return bookRepository.countByAuthor(author);
    }

    @Override
    public List<BookDTO> getAllBooksSortedByYearDesc() {
        // Recupera tutti i libri ordinati per anno di pubblicazione decrescente
        return bookRepository.findAllByOrderByAnnoDesc().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> searchBooksByTitleOrAuthor(String title, String author) {
        // Cerca libri per titolo OPPURE autore (ricerca combinata)
        return bookRepository.findByTitleOrAuthor(title, author).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converte un'entità Book in un DTO BookDTO
     * @param bookEntity l'entità da convertire
     * @return il DTO convertito
     */
    private BookDTO toDTO(Book bookEntity) {
        return new BookDTO(
                bookEntity.getId(),
                bookEntity.getTitle(),
                bookEntity.getAuthor(),
                bookEntity.getAnno(),
                bookEntity.getGenre()
        );
    }

    /**
     * Converte un DTO BookDTO in un'entità Book
     * @param bookDTO il DTO da convertire
     * @return l'entità convertita
     */
    private Book toEntity(BookDTO bookDTO) {
        return new Book(
                bookDTO.id(),
                bookDTO.title(),
                bookDTO.author(),
                bookDTO.year(),
                bookDTO.genre()
        );
    }
}