package br.com.bookmark.repository;

import br.com.bookmark.domain.Bookmark;
import br.com.bookmark.domain.id.BookmarkId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookmarkRepository extends JpaRepository<Bookmark, BookmarkId> {

    //@Query("select bm from Bookmark bm where bm.id.user.id = :userId")
    List<Bookmark> findByIdUserId(@Param(value = "userId") UUID userId);

    Optional<Bookmark> findByIdUserIdAndIdBookId(UUID userId, UUID bookId);

    void deleteByIdUserIdAndIdBookId(UUID userId, UUID bookId);

}
