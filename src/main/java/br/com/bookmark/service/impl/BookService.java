package br.com.bookmark.service.impl;

import br.com.bookmark.domain.Book;
import br.com.bookmark.exception.NotFoundException;
import br.com.bookmark.repository.BookRepository;
import br.com.bookmark.service.BookServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService implements BookServiceInterface {

    private final int ITEMS_PER_PAGE = 10;

    private final BookRepository repository;

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Book> findAll(String orderBy, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(Sort.Direction.DESC, orderBy));
        return repository.findAll(pageRequest);
    }

    @Override
    public Book findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not founded. Id: " + id));
    }

    @Override
    public Book save(Book book) {
        book.setId(null);
        return repository.save(book);
    }

    @Override
    public Book update(UUID id, Book book) {
        book.setId(findById(id).getId());
        return repository.save(book);
    }

    @Override
    public void delete(UUID id) {
        findById(id);
        repository.deleteById(id);
    }
}
