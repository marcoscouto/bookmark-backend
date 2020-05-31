package br.com.bookmark.service;

import br.com.bookmark.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {

    List<User> findAll();

    User findById(UUID id);

    User save(User user, MultipartFile profile) throws NoSuchAlgorithmException;

    User update(UUID id, User user, MultipartFile profile);

    void delete(UUID id);

    User findByEmail(String email);

}
