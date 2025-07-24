package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;
import ru.mts.media.platform.umc.domain.event.EventDomainService;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class EventDgsQuery {

    private final EventDomainService domainService;

    @DgsData(parentType = "VenueRecent", field = "recentEvents")
    public List<Event> recentEvents(DgsDataFetchingEnvironment env) {
        Venue venue = env.getSource();
        Integer limit = env.getArgumentOrDefault("limit", 5);
        return domainService.findByVenue(venue, limit);
    }

    @DgsQuery
    public List<Event> events() {
        return domainService.findAll();
    }

}
