package br.com.bookmark.service;

import br.com.bookmark.domain.Bookmark;

import java.util.List;
import java.util.UUID;

public interface BookmarkServiceInterface {

    List<Bookmark> findAll();

    List<Bookmark> findByUserId(UUID userId);

    Bookmark findByUserIdAndBookId(UUID userId, UUID bookId);

    Bookmark save(Bookmark bookmark);

    Bookmark update(UUID userId, UUID bookId, Bookmark bookmark);

    void delete(UUID userId, UUID bookId);

}
