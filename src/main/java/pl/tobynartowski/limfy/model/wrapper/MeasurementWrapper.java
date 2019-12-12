package pl.tobynartowski.limfy.model.wrapper;

import org.springframework.hateoas.server.core.Relation;

import java.sql.Timestamp;

@Relation(collectionRelation = "measurements")
public class MeasurementWrapper implements MeasurementProjection {

    private Double heartbeatAverage;
    private Integer stepsSum;
    private Timestamp timestamp;

    public MeasurementWrapper(Double heartbeatAverage, Integer stepsSum, Timestamp timestamp) {
        this.heartbeatAverage = heartbeatAverage;
        this.stepsSum = stepsSum;
        this.timestamp = timestamp;
    }

    @Override
    public Double getHeartbeatAverage() {
        return heartbeatAverage;
    }

    @Override
    public Integer getStepsSum() {
        return stepsSum;
    }

    @Override
    public Timestamp getTimestamp() {
        return timestamp;
    }
}
