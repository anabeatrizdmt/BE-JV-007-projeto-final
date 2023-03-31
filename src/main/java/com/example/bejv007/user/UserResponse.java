package com.example.bejv007.user;

import com.example.bejv007.user.dto.UserDTO;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String username;
    private String email;


}
