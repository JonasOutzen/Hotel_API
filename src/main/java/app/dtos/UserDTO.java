package app.dtos;

import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class UserDTO {
    private String username;
    private String password;
    Set<String> roles = new HashSet();

}
