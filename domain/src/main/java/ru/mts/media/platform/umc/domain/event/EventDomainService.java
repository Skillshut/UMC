package ru.mts.media.platform.umc.domain.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.mts.media.platform.umc.domain.gql.types.CreateEventInput;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.event.EventDomainServiceMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventDomainService {
    private final ApplicationEventPublisher eventPublisher;
    private final EventSot sot;
    private final EventDomainServiceMapper mapper;

    public EventSave save(CreateEventInput input) {
        Event event = mapper.toEntity(input);

        EventSave saved = new EventSave(sot.save(event, input.getVenueReferenceId()));
        eventPublisher.publishEvent(saved);

        return saved;
    }

    public List<Event> findByVenue(Venue venue, Integer limit) {
        List<Event> saved = sot.findByVenueReferenceId(venue.getId(), limit)
                .stream()
                .toList();

        eventPublisher.publishEvent(saved);
        return saved;
    }

    public List<Event> findAll() {
        List<Event> fromDb = sot.findAll();
        eventPublisher.publishEvent(fromDb);
        return fromDb;
    }

}
