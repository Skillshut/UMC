package ru.mts.media.platform.umc.domain.event;

import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.util.List;

public interface EventSot {
    Event save(Event event, String referenceId);
    List<Event> findByVenueReferenceId(String referenceId, Integer limit);

    List<Event> findAll();
}
