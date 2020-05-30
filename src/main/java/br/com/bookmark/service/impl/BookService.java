package br.com.bookmark.service.impl;

import br.com.bookmark.domain.Book;
import br.com.bookmark.exception.NotFoundException;
import br.com.bookmark.repository.BookRepository;
import br.com.bookmark.service.BookServiceInterface;
import br.com.bookmark.utils.UploadImageS3;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService implements BookServiceInterface {

    private final int ITEMS_PER_PAGE = 10;

    private final BookRepository repository;
    private final UploadImageS3 s3;

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

    @Transactional
    @Override
    public Book save(Book book, MultipartFile cover) {
        book.setId(null);
        book.setIsbn(book.getIsbn().replace("-", ""));
        URI uri = s3.uploadFile(cover, book.getIsbn(), "book");
        book.setCover(uri);
        return repository.save(book);
    }

    @Override
    public Book update(UUID id, Book newBook) {
        Book book = findById(id);
        newBook.setId(book.getId());
        newBook.setCreatedAt(book.getCreatedAt());
        newBook.setIsbn(newBook.getIsbn().replace("-", ""));
        return repository.save(newBook);
    }

    @Override
    public void delete(UUID id) {
        findById(id);
        repository.deleteById(id);
    }
}
