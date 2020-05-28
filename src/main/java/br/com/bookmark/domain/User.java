package br.com.bookmark.domain;

import br.com.bookmark.domain.enums.Permission;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @JsonIgnore
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    private String gender;

    @Past
    @DateTimeFormat
    private LocalDate birthDate;

    @NotNull
    private Permission permission;

    @Size(max = 255)
    private String profilePicture;

    @CreationTimestamp
    @FutureOrPresent
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @FutureOrPresent
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "id.user")
    private List<Bookmark> bookmarks;
}
