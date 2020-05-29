package br.com.bookmark.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Login implements Serializable {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
