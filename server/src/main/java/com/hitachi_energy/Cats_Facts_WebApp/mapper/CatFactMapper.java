package com.hitachi_energy.Cats_Facts_WebApp.mapper;

import com.hitachi_energy.Cats_Facts_WebApp.dto.CatFactDto;
import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CatFactMapper {
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "factDescription", source = "fact.description")
    CatFactDto toCatFactDTO(User user, Fact fact);
}
