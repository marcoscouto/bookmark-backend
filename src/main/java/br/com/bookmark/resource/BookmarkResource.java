package br.com.bookmark.resource;

import br.com.bookmark.domain.Bookmark;
import br.com.bookmark.service.impl.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkResource {

    private final BookmarkService service;

    @GetMapping("/{userId}/{bookId}")
    public ResponseEntity<Bookmark> findById(@PathVariable UUID userId, @PathVariable UUID bookId){
        Bookmark response = service.findByUserIdAndBookId(userId, bookId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/page/{page}/{isWishList}")
    public ResponseEntity<Page<Bookmark>> findByIdIsWishListOrBookmark(
            @PathVariable UUID userId,
            @PathVariable Integer page,
            @PathVariable Boolean isWishList
    ){
        Page<Bookmark> response = service.findByIdUserIdAndIsWishList(userId, isWishList, page);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Bookmark> save(@RequestBody Bookmark bookmark){
        Bookmark response = service.save(bookmark);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{userId}/{bookId}")
    public ResponseEntity<Bookmark> update(@PathVariable UUID userId, @PathVariable UUID bookId, @RequestBody Bookmark bookmark){
        Bookmark response = service.update(userId, bookId, bookmark);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<Void> delete(@PathVariable UUID userId, @PathVariable UUID bookId){
        service.delete(userId, bookId);
        return ResponseEntity.ok().build();
    }

}
