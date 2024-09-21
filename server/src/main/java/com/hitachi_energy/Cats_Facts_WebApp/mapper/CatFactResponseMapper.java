package com.hitachi_energy.Cats_Facts_WebApp.mapper;

import com.hitachi_energy.Cats_Facts_WebApp.dto.CatFactResponse;
import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CatFactResponseMapper {

    CatFactResponseMapper INSTANCE = Mappers.getMapper(CatFactResponseMapper.class);

    @Mapping(target = "description", source = "text")
    Fact toFact(CatFactResponse catFactResponse);
}
