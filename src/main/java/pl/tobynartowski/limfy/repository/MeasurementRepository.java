package pl.tobynartowski.limfy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import pl.tobynartowski.limfy.model.persitent.Measurement;
import pl.tobynartowski.limfy.model.projection.MeasurementProjection;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    @RestResource(path = "by-user")
    Page<Measurement> findByUserId(@Param("id") UUID id, Pageable page);

    @Query(value = "SELECT m.timestamp, AVG((((100 - m.shakiness) * 1.61803) * m.heartbeat) / 100) AS HeartbeatAverage, COUNT(m.steps) AS StepsSum " +
            "FROM measurement m WHERE m.user_id = :id " +
            "GROUP BY DAY(m.timestamp) ORDER BY m.timestamp DESC", nativeQuery = true)
    List<MeasurementProjection> findHeartbeatMeasurements(@Param("id") String id);
}