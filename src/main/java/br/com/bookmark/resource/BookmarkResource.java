package br.com.bookmark.resource;

import br.com.bookmark.domain.Bookmark;
import br.com.bookmark.service.impl.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bookmarks")
@RequiredArgsConstructor
public class BookmarkResource {

    private final BookmarkService service;

    @GetMapping
    public ResponseEntity<List<Bookmark>> findAll(){
        List<Bookmark> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Bookmark>> findByUserId(@PathVariable UUID userId){
        List<Bookmark> response = service.findByUserId(userId);
        return ResponseEntity.ok(response);
    }

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

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<Bookmark> save(@PathVariable UUID userId, @PathVariable UUID bookId, @RequestBody Bookmark bookmark){
        Bookmark response = service.save(userId, bookId, bookmark);
        return ResponseEntity.ok(response);
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
