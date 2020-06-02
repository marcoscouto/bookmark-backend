package br.com.bookmark.service.impl;

import br.com.bookmark.domain.User;
import br.com.bookmark.domain.enums.Permission;
import br.com.bookmark.exception.NotFoundException;
import br.com.bookmark.repository.UserRepository;
import br.com.bookmark.service.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {

    private final UserRepository repository;
    private final UploadImageS3 s3;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not founded. Id: " + id ));
    }

    @Override
    public User save(User user, MultipartFile profile) throws NoSuchAlgorithmException {
        user.setId(null);
        user.setPermission(Permission.USER);
        user.setIsAccountActive(false);
        if (profile != null) {
            String filename = DigestUtils.md5Hex(user.getEmail());
            URI uri = s3.uploadFile(profile, filename, "profile");
            user.setProfilePicture(uri);
        }
        return repository.save(user);
    }

    @Override
    public User update(UUID id, User newUser, MultipartFile profile) {
        User user = findById(id);
        newUser.setId(user.getId());
        newUser.setPermission(user.getPermission());
        newUser.setCreatedAt(user.getCreatedAt());
        newUser.setProfilePicture(user.getProfilePicture());
        if (profile != null) {
            String filename = DigestUtils.md5Hex(user.getEmail());
            URI uri = s3.uploadFile(profile, filename, "profile");
            newUser.setProfilePicture(uri);
        }
        return repository.save(newUser);
    }

    @Override
    public void delete(UUID id) {
        findById(id);
        repository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not founded. Email: " + email));
    }
}
