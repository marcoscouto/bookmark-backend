package br.com.bookmark.service;

import br.com.bookmark.domain.Book;

import java.util.List;
import java.util.UUID;

public interface BookServiceInterface {

    List<Book> findAll();

    Book findById(UUID id);

    Book save(Book book);

    Book update(UUID id, Book book);

    void delete(UUID id);

}
