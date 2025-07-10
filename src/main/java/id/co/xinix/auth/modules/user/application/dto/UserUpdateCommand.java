package id.co.xinix.auth.modules.user.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateCommand {

    private Long id;

    @NotBlank(message = "must be not blank")
    @NotNull(message = "must be not null")
    private String first_name;

    private String last_name;

    @NotBlank(message = "must be not blank")
    @NotNull(message = "must be not null")
    private String email;

    @NotBlank(message = "must be not blank")
    @NotNull(message = "must be not null")
    private String username;

    @NotNull(message = "role must not be null")
    @Size(min = 1, message = "role must not be empty")
    private List<@NotBlank(message = "role item must not be blank") String> role;

    private PhotoDTO photo;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PhotoDTO {
        private String bucket;
        private String path;
        private String mime;
        private String filename;
        private String originalFilename;
    }
}
