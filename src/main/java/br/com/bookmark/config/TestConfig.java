package br.com.bookmark.config;

import br.com.bookmark.domain.Book;
import br.com.bookmark.domain.Bookmark;
import br.com.bookmark.domain.User;
import br.com.bookmark.domain.enums.Permission;
import br.com.bookmark.domain.id.BookmarkId;
import br.com.bookmark.repository.BookRepository;
import br.com.bookmark.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User(null, "Marcos Couto", "marcos@gmail.com", "123456", null, LocalDate.of(1990, 9, 14), Permission.ADMIN, null, null, null, null);
        User user2 = new User(null, "Vinicius Couto", "vinicius@gmail.com", "123456", null, LocalDate.of(1990, 9, 14), Permission.USER, null, null, null, null);

        userRepository.saveAll(Arrays.asList(user1, user2));

        Book book1 = new Book(null, "Harry Potter e a Pedra Filosofal", Arrays.asList("JK Rowling"), Arrays.asList("Ficção"), "Rocco", null, null, null, null, null);
        Book book2 = new Book(null, "Harry Potter e a Câmara Secreta", Arrays.asList("JK Rowling"), Arrays.asList("Ficção"), "Rocco", null, null, null, null, null);

        bookRepository.saveAll(Arrays.asList(book1, book2));

        Bookmark bookmark1 = new Bookmark(new BookmarkId(user1, book1), 22, null, false, false, null, null);
        Bookmark bookmark2 = new Bookmark(new BookmarkId(user2, book2), 33, null, false, false, null, null);


    }



}
