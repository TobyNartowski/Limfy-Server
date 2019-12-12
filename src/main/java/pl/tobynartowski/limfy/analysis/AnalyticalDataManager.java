package pl.tobynartowski.limfy.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tobynartowski.limfy.model.persitent.RiskFactor;
import pl.tobynartowski.limfy.repository.DiseaseRepository;
import pl.tobynartowski.limfy.repository.RiskFactorRepository;

import java.util.List;

@Service
public class AnalyticalDataManager {

    private RiskFactorRepository riskFactorRepository;
    private DiseaseRepository diseaseRepository;

    @Autowired
    public AnalyticalDataManager(RiskFactorRepository riskFactorRepository, DiseaseRepository diseaseRepository) {
        this.riskFactorRepository = riskFactorRepository;
        this.diseaseRepository = diseaseRepository;
    }

    private void checkRiskFactors() {
        List<RiskFactor> riskFactor = riskFactorRepository.findAll();
        // TODO: check risk factor and diseases from enum and load them to database
    }

    private void checkDiseases() {
    }

    public void checkDatabaseIntegrity() {
        checkRiskFactors();
        checkDiseases();
    }
}
