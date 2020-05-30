package br.com.bookmark.service.impl;

import br.com.bookmark.domain.Bookmark;
import br.com.bookmark.domain.id.BookmarkId;
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
    public Page<Bookmark> findByIdUserIdAndIsWishList(UUID userId, Boolean isWishList , Integer page) {
        PageRequest pageRequest = PageRequest.of(page, ITEMS_PER_PAGE, Sort.by(Sort.Direction.DESC, "updatedAt" ));
        return repository.findByIdUserIdAndIsWishList(userId, isWishList, pageRequest);
    }

    @Override
    public Bookmark save(Bookmark bookmark) {
        bookmark.setId(null);
        return repository.save(bookmark);
    }

    @Override
    public Bookmark update(UUID userId, UUID bookId, Bookmark bookmark) {
        bookmark.setId(findByUserIdAndBookId(userId, bookId).getId());
        return repository.save(bookmark);
    }

    @Override
    public void delete(UUID userId, UUID bookId) {
        repository.deleteByIdUserIdAndIdBookId(userId, bookId);
    }
}
