package br.com.bookmark.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @ElementCollection
    @CollectionTable(name = "tb_authors")
    private List<String> authors;

    @ElementCollection
    @CollectionTable(name = "tb_genders")
    private List<String> genders;

    @Size(max = 100)
    private String publisher;

    @ISBN
    @NotBlank
    @Column(unique = true)
    private String isbn;

    @Lob
    @Size(max = 500)
    private String synopsis;

    private URI cover;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @FutureOrPresent
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "id.book")
    private List<Bookmark> bookmarks;

}
