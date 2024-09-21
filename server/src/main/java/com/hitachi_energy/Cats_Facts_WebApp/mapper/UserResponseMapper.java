package com.hitachi_energy.Cats_Facts_WebApp.mapper;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserResponse;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import com.hitachi_energy.Cats_Facts_WebApp.utils.UserUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    @Mapping(target = "name", source = "userResponse")
    User toUser(UserResponse userResponse);

    default String mapFullName(UserResponse userResponse) {
        return UserUtils.getFullName(userResponse);
    }
}
