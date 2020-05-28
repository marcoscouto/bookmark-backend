package br.com.bookmark.domain.id;

import br.com.bookmark.domain.Book;
import br.com.bookmark.domain.User;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BookmarkId implements Serializable {

    private User userId;
    private Book bookId;

}
