package id.co.xinix.auth.modules.user.application.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDTO implements Serializable {

    private Long id;

    private String username;

    private String email;

    private String password;
}
