package br.com.bookmark.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
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

    @Lob
    @Size(max = 500)
    private String synopsis;

    private String cover;

}
