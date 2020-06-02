package br.com.bookmark.resource;

import br.com.bookmark.domain.Book;
import br.com.bookmark.domain.User;
import br.com.bookmark.domain.dto.SignUp;
import br.com.bookmark.service.impl.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> response = userService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        User response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestParam String user, @RequestParam(required = false) MultipartFile profile) {
        try {
            ObjectMapper obj = new ObjectMapper();
            SignUp response = obj.readValue(user, SignUp.class);
            User userResponse = userService.save(new User(response), profile);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (JsonProcessingException | NoSuchAlgorithmException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/activeaccount/{id}")
    public ResponseEntity<Void> save(@PathVariable UUID id) {
        userService.activeAccount(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestParam String user, @RequestParam(required = false) MultipartFile profile) {
        try {
            ObjectMapper obj = new ObjectMapper();
            User response = obj.readValue(user, User.class);
            response = userService.update(id, response, profile);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
