package ru.mts.media.platform.umc.dao.postgres.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgEntity;
import ru.mts.media.platform.umc.dao.postgres.venue.VenuePgRepository;
import ru.mts.media.platform.umc.domain.event.EventSave;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.event.EventSot;

import java.util.List;

@Component
@RequiredArgsConstructor
class EventPgDao implements EventSot {
    private final EventPgRepository eventPgRepository;
    private final VenuePgRepository venuePgRepository;
    private final EventPgMapper mapper;

    public Event save(Event event, String referenceId) {
        VenuePgEntity venuePgEntity = venuePgRepository.findByReferenceId(referenceId);
        EventPgEntity eventPgEntity = mapper.asEntity(event);
        eventPgEntity.getVenues().add(venuePgEntity);

        EventPgEntity saved = eventPgRepository.save(eventPgEntity);
        venuePgEntity.getEvents().add(saved);
        venuePgRepository.save(venuePgEntity);

        Event result = mapper.asModel(saved);
        return result;
    }



    @Override
    public List<Event> findByVenueReferenceId(String referenceId, Integer limit) {
        return eventPgRepository.findRecentByVenueReferenceId(referenceId, PageRequest.of(0, limit))
                .stream()
                .map(mapper::asModel)
                .toList();
    }

    @Override
    public List<Event> findAll() {
        return eventPgRepository.findAll()
                .stream()
                .map(mapper::asModel)
                .toList();
    }
}
