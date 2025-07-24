package ru.mts.media.platform.umc.domain.venue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.SaveVenueInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class VenueDomainService {
    private final ApplicationEventPublisher eventPublisher;
    private final VenueSot sot;
    private final VenueDomainServiceMapper mapper;

    public VenueSave save(FullExternalId id, SaveVenueInput input) {
        var evt = sot.getVenueById(id)
                .map(applyPatch(input))
                .map(VenueSave::new)
                .orElse(null);

        eventPublisher.publishEvent(evt);

        return evt;
    }

    public Venue findByEvent(Event event) {
            Venue fromDb = sot.findByEvent(event);

            eventPublisher.publishEvent(fromDb);
            return fromDb;
    }

    private Function<Venue, Venue> applyPatch(SaveVenueInput updates) {
        return x -> mapper.patch(x, updates);
    }

    public List<Venue> getVenues() {
        List<Venue> venues = sot.getVenues();
        eventPublisher.publishEvent(venues);
        return venues;
    }

    public Venue getVenueByReferenceId(String referenceId) {
        Venue venue =  sot.getVenueByReferenceId(referenceId).orElse(null);
        eventPublisher.publishEvent(venue);
        return venue;
    }
}
