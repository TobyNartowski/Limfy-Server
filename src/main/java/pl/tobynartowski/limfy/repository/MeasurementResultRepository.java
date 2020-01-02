package pl.tobynartowski.limfy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import pl.tobynartowski.limfy.model.persitent.MeasurementResult;
import pl.tobynartowski.limfy.model.wrapper.MeasurementProjection;

@RestResource(exported = false)
@RepositoryRestResource
public interface MeasurementResultRepository extends JpaRepository<MeasurementResult, Long> {

    @RestResource(exported = false)
    @Query(value = "SELECT mr.timestamp AS timestamp, ROUND(mr.heartbeat, 2) AS heartbeatAverage, mr.steps AS stepsSum " +
            "FROM measurement_result mr WHERE mr.user_id = :userId", nativeQuery = true)
    Page<MeasurementProjection> getMeasurementResults(@Param("userId") String userId, Pageable pageable);
}
