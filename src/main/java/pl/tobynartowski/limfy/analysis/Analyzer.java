package pl.tobynartowski.limfy.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.tobynartowski.limfy.analysis.classification.BodyDataClassificationProcessor;
import pl.tobynartowski.limfy.analysis.classification.BodyDataClassified;
import pl.tobynartowski.limfy.model.persitent.Analysis;
import pl.tobynartowski.limfy.model.persitent.Disease;
import pl.tobynartowski.limfy.model.persitent.User;
import pl.tobynartowski.limfy.model.wrapper.MeasurementProjection;
import pl.tobynartowski.limfy.repository.AnalysisRepository;
import pl.tobynartowski.limfy.repository.DiseaseRepository;
import pl.tobynartowski.limfy.repository.MeasurementRepository;
import pl.tobynartowski.limfy.repository.MeasurementResultRepository;

@Service
public class Analyzer {

    private MeasurementResultRepository measurementResultRepository;
    private BodyDataClassificationProcessor classificationProcessor;
    private DiseaseRepository diseaseRepository;
    private AnalysisRepository analysisRepository;

    @Autowired
    public Analyzer(MeasurementResultRepository measurementResultRepository, BodyDataClassificationProcessor classificationProcessor,
                    DiseaseRepository diseaseRepository,
                    AnalysisRepository analysisRepository) {
        this.measurementResultRepository = measurementResultRepository;
        this.classificationProcessor = classificationProcessor;
        this.diseaseRepository = diseaseRepository;
        this.analysisRepository = analysisRepository;
    }

    public void analyzeUserData(User user) {
        Page<MeasurementProjection> userMeasurements =
                measurementResultRepository.getMeasurementResults(user.getId().toString(), Pageable.unpaged());

        double heartbeatAverage = userMeasurements
                .stream()
                .mapToDouble(MeasurementProjection::getHeartbeatAverage)
                .average()
                .orElse(0.0);
        double stepsAverage = userMeasurements
                .stream()
                .mapToDouble(MeasurementProjection::getStepsSum)
                .average()
                .orElse(0.0);

        BodyDataClassified dataClassified = classificationProcessor.classifyData(heartbeatAverage, stepsAverage, user.getBodyData());
        for (Disease disease : diseaseRepository.findAll()) {
            double riskFactorAverage = disease.getRiskFactors().stream()
                    .mapToDouble(r -> dataClassified.of(r.of()).getValue())
                    .average()
                    .orElse(0.0);

            Analysis analysis = analysisRepository.findByDisease(disease);
            if (analysis == null) {
                analysis = new Analysis(disease, riskFactorAverage, user);
            } else {
                analysis.setPercentage(riskFactorAverage);
            }

            analysisRepository.save(analysis);
        }
    }
}
