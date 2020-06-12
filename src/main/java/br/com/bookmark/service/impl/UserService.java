package br.com.bookmark.service.impl;

import br.com.bookmark.domain.User;
import br.com.bookmark.domain.enums.Permission;
import br.com.bookmark.exception.NotFoundException;
import br.com.bookmark.repository.UserRepository;
import br.com.bookmark.service.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final EmailService emailService;
    private final PasswordEncoder encoder;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not founded. Id: " + id));
    }

    @Override
    public User save(User user){
        user.setId(null);
        user.setPermission(Permission.USER);
        user.setIsAccountActive(false);
        user.setPassword(encoder.encode(user.getPassword()));
        user = repository.save(user);
        sendAccountConfirmationEmail(user.getEmail(), user.getName(), user.getId());
        return user;
    }

    @Override
    public User update(UUID id, User newUser, MultipartFile profile) {
        User user = findById(id);
        newUser.setId(user.getId());
        newUser.setPermission(user.getPermission());
        newUser.setCreatedAt(user.getCreatedAt());
        newUser.setUpdatedAt(null);
        newUser.setProfilePicture(user.getProfilePicture());
        newUser.setIsAccountActive(user.getIsAccountActive());
        if (profile != null) {
            String filename = DigestUtils.md5Hex(user.getEmail());
            URI uri = s3.uploadFile(profile, filename, "profile");
            newUser.setProfilePicture(uri);
        }
        if (newUser.getPassword() != null) {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
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

    @Override
    public void activeAccount(UUID id) {
        User user = findById(id);
        user.setIsAccountActive(true);
        user.setUpdatedAt(null);
        repository.save(user);
    }

    @Override
    public void forgotPassword(String email) {
        User user = findByEmail(email);
        String newPassword = generateNewPassword();
        user.setPassword(encoder.encode(newPassword));
        user.setUpdatedAt(null);
        sendForgotPasswordEmail(user.getEmail(), user.getName(), newPassword);
        repository.save(user);
    }

    private void sendAccountConfirmationEmail(String email, String name, UUID id) {
        emailService.sendAccountConfirmationEmail(email, name, id);
    }

    private void sendForgotPasswordEmail(String email, String name, String password) {
        emailService.sendChangePasswordEmail(email, name, password);
    }

    public static String generateNewPassword() {
        return RandomStringUtils.randomAlphanumeric(15);
    }

}
