package pl.tobynartowski.limfy.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tobynartowski.limfy.model.data.DiseaseData;
import pl.tobynartowski.limfy.model.data.RiskFactorData;
import pl.tobynartowski.limfy.model.persitent.Disease;
import pl.tobynartowski.limfy.model.persitent.RiskFactor;
import pl.tobynartowski.limfy.repository.DiseaseRepository;
import pl.tobynartowski.limfy.repository.RiskFactorRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        for (RiskFactorData entry : RiskFactorData.values()) {
            if (riskFactor.stream().filter(r -> r.getName().equals(entry.getName())).findAny().isEmpty()) {
                riskFactorRepository.save(new RiskFactor(entry.getName()));
            }
        }
    }

    private void checkDiseases() {
        List<Disease> diseases = diseaseRepository.findAll();
        for (DiseaseData diseaseEntry : DiseaseData.values()) {
            Disease disease = diseases.stream()
                    .filter(r -> r.getName().equals(diseaseEntry.getName()))
                    .findAny()
                    .orElseGet(() -> new Disease(diseaseEntry.getName(), diseaseEntry.getHealthInfluence()));
            for (RiskFactorData riskFactorData : RiskFactorData.values()) {
                RiskFactor riskFactor = riskFactorRepository.findByName(riskFactorData.getName());
                if (!disease.getRiskFactors().contains(riskFactor)) {
                    disease.addRiskFactor(riskFactor);
                }
            }
            diseaseRepository.save(disease);
        }
    }

    public void checkDatabaseIntegrity() {
        checkRiskFactors();
        checkDiseases();
    }
}
