package br.com.bookmark.service.impl;

import br.com.bookmark.domain.Book;
import br.com.bookmark.domain.Bookmark;
import br.com.bookmark.domain.User;
import br.com.bookmark.domain.id.BookmarkId;
import br.com.bookmark.exception.AlreadyExistsException;
import br.com.bookmark.exception.NotFoundException;
import br.com.bookmark.repository.BookmarkRepository;
import br.com.bookmark.service.BookmarkServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkService implements BookmarkServiceInterface {

    private final int ITEMS_PER_PAGE = 10;

    private final BookmarkRepository repository;
    private final BookService bookService;
    private final UserService userService;

    @Override
    public List<Bookmark> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Bookmark> findByUserId(UUID userId) {
        return repository.findByIdUserId(userId);
    }

    @Override
    public Bookmark findByUserIdAndBookId(UUID userId, UUID bookId) {
        return repository.findByIdUserIdAndIdBookId(userId, bookId).orElseThrow(() -> new NotFoundException("Bookmark not founded"));
    }

    @Override
    public Page<Bookmark> findByIdUserIdAndIsWishList(UUID userId, Boolean isWishList, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return repository.findByIdUserIdAndIsWishList(userId, isWishList, pageRequest);
    }

    @Override
    public Bookmark save(UUID userId, UUID bookId, Bookmark bookmark) {
        if (repository.findByIdUserIdAndIdBookId(userId, bookId).isPresent())
            throw new AlreadyExistsException("Bookmark already exists");
        User user = userService.findById(userId);
        Book book = bookService.findById(bookId);
        bookmark.setId(new BookmarkId(user, book));
        return repository.save(bookmark);
    }

    @Override
    public Bookmark update(UUID userId, UUID bookId, Bookmark newBookmark) {
        Bookmark bookmark = findByUserIdAndBookId(userId, bookId);
        newBookmark.setId(bookmark.getId());
        newBookmark.setCreatedAt(bookmark.getCreatedAt());
        return repository.save(newBookmark);
    }

    @Override
    public void delete(UUID userId, UUID bookId) {
        repository.deleteByIdUserIdAndIdBookId(userId, bookId);
    }
}
