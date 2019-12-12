package pl.tobynartowski.limfy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import pl.tobynartowski.limfy.model.persitent.Measurement;
import pl.tobynartowski.limfy.model.wrapper.MeasurementProjection;

import java.util.UUID;

@RepositoryRestResource
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    @RestResource(exported = false)
    @Query(value = "SELECT m.timestamp AS timestamp, " +
            "ROUND(AVG((((100 - m.shakiness) * 1.61803) * m.heartbeat) / 100), 2) AS heartbeatAverage, " +
            "COUNT(m.steps) AS stepsSum " +
            "FROM measurement m WHERE m.user_id = :id " +
            "GROUP BY DAY(m.timestamp) ORDER BY m.timestamp DESC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM (SELECT * FROM measurement m WHERE m.user_id = :id GROUP BY DAY(m.timestamp)) AS Results",
            nativeQuery = true)
    Page<MeasurementProjection> findMeasurementAverages(@Param("id") String id, Pageable pageable);
}