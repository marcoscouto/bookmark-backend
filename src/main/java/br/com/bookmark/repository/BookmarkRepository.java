package br.com.bookmark.repository;

import br.com.bookmark.domain.Bookmark;
import br.com.bookmark.domain.id.BookmarkId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

    @Transactional(readOnly = true)
    List<Bookmark> findByIdUserId(UUID userId);

    @Transactional(readOnly = true)
    Optional<Bookmark> findByIdUserIdAndIdBookId(UUID userId, UUID bookId);

    void deleteByIdUserIdAndIdBookId(UUID userId, UUID bookId);

    @Transactional(readOnly = true)
    Page<Bookmark> findByIdUserIdAndIsWishList(UUID userId, Boolean isWishList, Pageable pageable);

}
