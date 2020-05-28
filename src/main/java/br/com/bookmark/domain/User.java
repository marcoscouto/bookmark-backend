package br.com.bookmark.domain;

import br.com.bookmark.domain.enums.Permission;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)
    private UUID id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;


    private String gender;

    @Past
    @DateTimeFormat
    private LocalDate birthDate;

    @NotBlank
    private Permission permission;

    @Size(max = 255)
    private String profilePicture;

    @CreationTimestamp
    @FutureOrPresent
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @FutureOrPresent
    private LocalDateTime updatedAt;
}
