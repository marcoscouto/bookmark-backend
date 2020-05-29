package br.com.bookmark.service;

import br.com.bookmark.domain.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BookServiceInterface {

    List<Book> findAll();

    Page<Book> findAll(String orderBy, Integer page);

    Book findById(UUID id);

    Book save(Book book);

    Book update(UUID id, Book book);

    void delete(UUID id);

}
