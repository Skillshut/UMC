package ru.mts.media.platform.umc.api.gql.venue;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.test.EnableDgsTest;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.mts.media.platform.umc.api.gql.event.EventDgsQuery;
import ru.mts.media.platform.umc.domain.event.EventDomainService;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.gql.types.VenueRecent;
import ru.mts.media.platform.umc.domain.venue.VenueDomainService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@EnableDgsTest
@SpringBootTest(classes = {EventDgsQuery.class, VenueDgsQuery.class})
public class VenueDgsQueryTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    public EventDomainService eventService;

    @MockBean
    public VenueDomainService venueDomainService;


    @Test
    public void getEventsTest() {

        Venue venue = Venue.newBuilder()
                .id("venue1")
                .externalId(FullExternalId.newBuilder()
                        .externalId("external1")
                        .brandId("brand1")
                        .providerId("providerId1")
                        .build())
                .name("venue1name")
                .build();


        Event event1 = new Event();
        event1.setId("1");
        event1.setName("event1");
        event1.setStartTime(LocalDateTime.MIN.toString());
        event1.setEndTime(LocalDateTime.MAX.toString());
        event1.setVenues(List.of(venue));


        Event event2 = new Event();
        event2.setId("2");
        event2.setName("event2");
        event2.setStartTime(LocalDateTime.now().toString());
        event2.setEndTime(LocalDateTime.now().plusDays(1).toString());
        event2.setVenues(List.of(venue));

        List<Event> mockEvents = List.of(event1, event2);

        VenueRecent venueRecent = VenueRecent.newBuilder()
                .id("venue1")
                .externalId(FullExternalId.newBuilder()
                        .externalId("external1")
                        .brandId("brand1")
                        .providerId("providerId1")
                        .build())
                .name("venue1name")
                .recentEvents(mockEvents)
                .build();

        Mockito.when(eventService.findByVenue(venue, 2))
                .thenReturn(mockEvents);

        Mockito.when(venueDomainService.getVenues())
                .thenReturn(List.of(venue));

        @Language("GraphQL")
        var query = """
                 query{
                  venues {
                       name
                       id
                       recentEvents(limit: 2) {
                         name
                       }
                     }
                }
                """;

        Event result = dgsQueryExecutor.executeAndExtractJsonPathAsObject(query, "data.venues[0].recentEvents[0]", Event.class);

        verify(venueDomainService, times(1)).getVenues();
        verify(eventService, times(1)).findByVenue(any(), any());
        assertEquals("event1", result.getName());
    }
}