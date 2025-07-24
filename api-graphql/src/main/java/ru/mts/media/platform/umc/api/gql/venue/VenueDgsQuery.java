package ru.mts.media.platform.umc.api.gql.venue;

import com.netflix.graphql.dgs.*;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueDomainService;

import java.util.List;
import java.util.Optional;

@DgsComponent
@RequiredArgsConstructor
public class VenueDgsQuery {
    private final VenueDomainService domainService;

    @DgsQuery
    public Venue venueByReferenceId(@InputArgument String id) {
        return Optional.of(id).map(domainService::getVenueByReferenceId).get();
    }

    @DgsQuery
    public List<Venue> venues() {
        return domainService.getVenues();
    }

    @DgsData(parentType = "EventWithVenue", field = "venue")
    public Venue venue(DgsDataFetchingEnvironment env) {
        Event event = env.getSource();
        return domainService.findByEvent(event);
    }
}
