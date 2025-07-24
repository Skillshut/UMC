package ru.mts.media.platform.umc.dao.postgres.venue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSave;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VenuePgDao implements VenueSot {
    private final VenuePgRepository repository;
    private final VenuePgMapper mapper;

    public Optional<Venue> getVenueByReferenceId(String id) {
        return Optional.of(id)
                .map(repository::findByReferenceId)
                .map(mapper::asModel);
    }

    @Override
    public Optional<Venue> getVenueById(FullExternalId externalId) {
        return Optional.of(externalId)
                .map(mapper::asPk)
                .flatMap(repository::findById)
                .map(mapper::asModel);

    }

    @Override
    public List<Venue> getVenues() {
        return repository.findAll()
                .stream()
                .map(mapper::asModel)
                .toList();
    }

    @Override
    public Venue findByEvent(Event event) {
       VenuePgEntity venuePgEntity =  repository.findByEventId(event.getId());
       Venue result = mapper.asModel(venuePgEntity);
       return result;
    }

    @EventListener
    public void handleVenueCreatedEvent(VenueSave evt) {
        evt.unwrap()
                .map(mapper::asEntity)
                .ifPresent(repository::save);
    }
}
