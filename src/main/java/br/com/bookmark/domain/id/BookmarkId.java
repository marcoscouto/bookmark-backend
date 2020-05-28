package br.com.bookmark.domain.id;

import br.com.bookmark.domain.Book;
import br.com.bookmark.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkId implements Serializable {

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

}
