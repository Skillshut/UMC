package ru.mts.media.platform.umc.dao.postgres.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface EventPgRepository extends JpaRepository<EventPgEntity, Long> {
    @Query("""
    SELECT e FROM EventPgEntity e
    JOIN e.venues v
    WHERE v.referenceId = :referenceId
    ORDER BY e.startTime DESC
""")
    List<EventPgEntity> findRecentByVenueReferenceId(
            @Param("referenceId") String referenceId,
            Pageable pageable
    );

}
