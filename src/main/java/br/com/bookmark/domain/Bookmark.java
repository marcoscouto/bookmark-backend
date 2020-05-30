package br.com.bookmark.domain;

import br.com.bookmark.domain.id.BookmarkId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_bookmark")
public class Bookmark implements Serializable {

    @EmbeddedId
    private BookmarkId id;

    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private Integer page;

    @Lob
    @Size(max = 500)
    private String annotations;

    private Boolean isRead;

    private Boolean isWishList;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @FutureOrPresent
    private LocalDateTime updatedAt;

}
