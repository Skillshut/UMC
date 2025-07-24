package ru.mts.media.platform.umc.domain.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mts.media.platform.umc.domain.gql.types.CreateEventInput;
import ru.mts.media.platform.umc.domain.gql.types.Event;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
interface EventDomainServiceMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    Event toEntity(CreateEventInput input);
}
