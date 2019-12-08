package pl.tobynartowski.limfy.model.projection;

import java.sql.Timestamp;

public interface MeasurementProjection {

    Double getHeartbeatAverage();
    Integer getStepsSum();
    Timestamp getTimestamp();
}
