package pl.tobynartowski.limfy.model.wrapper;

import java.sql.Timestamp;

public interface MeasurementProjection {

    Double getHeartbeatAverage();
    Integer getStepsSum();
    Timestamp getTimestamp();
}
