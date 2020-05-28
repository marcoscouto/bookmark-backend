package br.com.bookmark.service.impl;

import br.com.bookmark.domain.Bookmark;
import br.com.bookmark.exception.NotFoundException;
import br.com.bookmark.repository.BookmarkRepository;
import br.com.bookmark.service.BookmarkServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkService implements BookmarkServiceInterface {

    private final BookmarkRepository repository;

    @Override
    public List<Bookmark> findAll() {
        return repository.findAll();
    }

    @Override
    public Bookmark findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Bookmark não encontrado. Id: " + id));
    }

    @Override
    public Bookmark save(Bookmark bookmark) {
        bookmark.setId(null);
        return repository.save(bookmark);
    }

    @Override
    public Bookmark update(UUID id, Bookmark bookmark) {
        bookmark.setId(findById(id).getId());
        return repository.save(bookmark);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
