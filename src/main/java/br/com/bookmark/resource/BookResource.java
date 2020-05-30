package br.com.bookmark.resource;

import br.com.bookmark.domain.Book;
import br.com.bookmark.service.impl.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookResource {

    private final BookService service;

    @GetMapping
    public ResponseEntity<List<Book>> findAll(){
        List<Book> response = service.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Book>> findAll(
            @PathVariable(value = "page", required = false) Integer page,
            @RequestParam(value = "order", defaultValue = "createdAt") String orderBy){
        Page<Book> response = service.findAll(orderBy, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable UUID id){
        Book response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestParam String book, @RequestParam MultipartFile cover) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        Book response = obj.readValue(book, Book.class);
        response = service.save(response, cover);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable UUID id, @RequestBody Book book){
        Book response = service.update(id, book);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
