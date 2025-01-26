package ro.ubb.mp.controller.dto.request;

import lombok.Builder;
import lombok.Data;
import ro.ubb.mp.dao.model.postgres.Role;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class UserProfilePictureRequestDTO {
    private String password;
    private String profilePicture;
    private Set<Long> interestAreaIds;
    @NotNull
    private Role role;
}
