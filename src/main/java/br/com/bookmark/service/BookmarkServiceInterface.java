package br.com.bookmark.service;

import br.com.bookmark.domain.Bookmark;

import java.util.List;
import java.util.UUID;

public interface BookmarkServiceInterface {

    List<Bookmark> findAll();

    Bookmark findById(UUID id);

    Bookmark save(Bookmark bookmark);

    Bookmark update(UUID id, Bookmark bookmark);

    void delete(UUID id);

}
