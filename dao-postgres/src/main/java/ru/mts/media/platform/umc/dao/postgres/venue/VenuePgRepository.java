package ru.mts.media.platform.umc.dao.postgres.venue;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mts.media.platform.umc.dao.postgres.common.FullExternalIdPk;

import java.util.List;

@Repository
public interface VenuePgRepository extends JpaRepository<VenuePgEntity, FullExternalIdPk> {

    VenuePgEntity findByReferenceId(String referenceId);
    @Query("""
    SELECT e FROM VenuePgEntity e
    JOIN e.events v
    WHERE v.id = :id
    ORDER BY e.name
    LIMIT 1
""")
    VenuePgEntity findByEventId(@Param("id") String id);
}
