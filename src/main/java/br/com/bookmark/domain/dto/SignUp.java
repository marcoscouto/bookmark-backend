package br.com.bookmark.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SignUp implements Serializable {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 50)
    private String password;
}
