package com.giuseppe.biblioteca.model;

public record BookDTO(
        Long id,
        String title,
        String author,
        int year,
        String genre) {
}
