package br.com.bookmark.service.impl;

import br.com.bookmark.domain.User;
import br.com.bookmark.exception.NotFoundException;
import br.com.bookmark.repository.UserRepository;
import br.com.bookmark.service.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("User not founded. Id: " + id ));
    }

    @Override
    public User save(User user) {
        user.setId(null);
        return repository.save(user);
    }

    @Override
    public User update(UUID id, User user) {
        user.setId(findById(id).getId());
        return repository.save(user);
    }

    @Override
    public void delete(UUID id) {
        findById(id);
        repository.deleteById(id);
    }
}
