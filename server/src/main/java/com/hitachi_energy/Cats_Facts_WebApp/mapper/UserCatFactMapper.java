package com.hitachi_energy.Cats_Facts_WebApp.mapper;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserCatFactDto;
import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserCatFactMapper {

    UserCatFactMapper INSTANCE = Mappers.getMapper(UserCatFactMapper.class);

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "factDescription", source = "fact.description")
    UserCatFactDto toUserCatFactDTO(User user, Fact fact);
}
