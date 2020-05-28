package br.com.bookmark.service;

import br.com.bookmark.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserServiceInterface {

    List<User> findAll();

    User findById(UUID id);

    User save(User user);

    User update(UUID id, User user);

    void delete(UUID id);

}
