package com.example.bejv007.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {

    @Pattern(regexp = "[\\w\\p{L}.]{5,20}")
    private String name;//a-zA-Zacentos0-9. tamanho de 5 a 20
    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;

}