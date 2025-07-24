package ru.mts.media.platform.umc.dao.postgres.venue;

import jakarta.persistence.*;
import lombok.Data;
import ru.mts.media.platform.umc.dao.postgres.common.FullExternalIdPk;
import ru.mts.media.platform.umc.dao.postgres.event.EventPgEntity;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@IdClass(FullExternalIdPk.class)
@Table(name = "venue",
        indexes = {
                @Index(name = "idx_venue_referenceId",
                        columnList = "referenceId",
                        unique = true)
        })
public class VenuePgEntity {
    @Id
    private String brand;

    @Id
    private String provider;

    @Id
    private String externalId;

    private String referenceId;

    private String name;
    @ManyToMany
    @JoinTable(
            name = "venue_event",
            joinColumns = {
                    @JoinColumn(name = "brand", referencedColumnName = "brand"),
                    @JoinColumn(name = "provider", referencedColumnName = "provider"),
                    @JoinColumn(name = "external_id", referencedColumnName = "externalId")
            },
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id")
    )
    private Set<EventPgEntity> events = new HashSet<>();
}
