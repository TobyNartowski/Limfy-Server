package pl.tobynartowski.limfy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import pl.tobynartowski.limfy.model.persitent.Measurement;
import pl.tobynartowski.limfy.model.persitent.User;
import pl.tobynartowski.limfy.model.wrapper.MeasurementProjection;

import java.util.Date;
import java.util.UUID;

@RepositoryRestResource
public interface MeasurementRepository extends JpaRepository<Measurement, UUID> {

    @Procedure(procedureName = "generate_results")
    void generateResults(@Param("id") String id);

    @RestResource(exported = false)
    @Query(value = "SELECT m.timestamp AS timestamp, " +
            "ROUND(AVG(((100 - m.shakiness) * m.heartbeat) / 100), 2) AS heartbeatAverage, " +
            "SUM(m.steps) AS stepsSum " +
            "FROM measurement m WHERE m.user_id = :id " +
            "GROUP BY DAY(m.timestamp) ORDER BY m.timestamp DESC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM (SELECT * FROM measurement m WHERE m.user_id = :id GROUP BY DAY(m.timestamp)) AS Results",
            nativeQuery = true)
    Page<MeasurementProjection> findMeasurementAverages2(@Param("id") String id, Pageable pageable);

    Long countMeasurementsByUser(User user);

    @RestResource(exported = false)
    @Query(value = "SELECT get_heartbeat_average(:user_id, :measurements_date)", nativeQuery = true)
    Double getTodayHeartbeatAverage(@Param("user_id") String id, @Param("measurements_date") Date date);
}