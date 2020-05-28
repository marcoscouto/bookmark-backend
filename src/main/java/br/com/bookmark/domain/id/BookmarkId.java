package br.com.bookmark.domain.id;

import br.com.bookmark.domain.Book;
import br.com.bookmark.domain.User;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class BookmarkId implements Serializable {

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

}
