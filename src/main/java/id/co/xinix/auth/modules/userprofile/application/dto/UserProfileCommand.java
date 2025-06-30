package id.co.xinix.auth.modules.userprofile.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserProfileCommand {

    private Long id;
    private String code;
    private String firstName;
    private String lastName;
    private String identityNumber;
    private String phoneNumber;
    private String photo;
    private String address;
    private Long userId;

}
