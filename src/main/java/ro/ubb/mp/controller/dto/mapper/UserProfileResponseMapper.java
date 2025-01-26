package ro.ubb.mp.controller.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.ubb.mp.controller.dto.response.user.UserProfileDTO;
import ro.ubb.mp.dao.model.postgres.User;

@Mapper(componentModel = "spring")
public interface UserProfileResponseMapper {

    @Mapping(target = "email", source = "username")
     UserProfileDTO toProfileDTO(User user);
}
