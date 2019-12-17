package pl.tobynartowski.limfy.analysis.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;
import pl.tobynartowski.limfy.analysis.Analyzer;
import pl.tobynartowski.limfy.model.persitent.Measurement;
import pl.tobynartowski.limfy.repository.MeasurementRepository;

@Component
@RepositoryEventHandler
public class DataListener {

    private static final int MEASUREMENTS_THRESHOLD = 100;
    private Logger logger = LoggerFactory.getLogger(DataListener.class);

    private MeasurementRepository measurementRepository;
    private Analyzer analyzer;

    @Autowired
    public DataListener(MeasurementRepository measurementRepository, Analyzer analyzer) {
        this.measurementRepository = measurementRepository;
        this.analyzer = analyzer;
    }

    @HandleAfterCreate
    public void interceptMeasurements(Measurement measurement) {
        if (measurement != null && measurement.getUser() != null) {
            if (measurementRepository.countMeasurementsByUser(measurement.getUser()) % MEASUREMENTS_THRESHOLD == 0) {
                logger.info("Analyzing data for user: " + measurement.getUser().getUsername());
                analyzer.analyzeUserData(measurement.getUser());
            }
        }
    }
}
