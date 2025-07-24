package ru.mts.media.platform.umc.dao.postgres.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgEntity;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EventPgMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "venues", ignore = true)
    EventPgEntity asEntity(Event event);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "venues", source = "venues")
    Event asModel(EventPgEntity eventPgEntity);

    @Mapping(target = "externalId.brandId", source = "brand")
    @Mapping(target = "externalId.providerId", source = "provider")
    @Mapping(target = "externalId.externalId", source = "externalId")
    @Mapping(target = "id", source = "referenceId")
    Venue asModel(VenuePgEntity venuePg);
}
