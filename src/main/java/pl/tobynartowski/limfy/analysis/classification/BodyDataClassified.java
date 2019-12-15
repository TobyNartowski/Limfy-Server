package pl.tobynartowski.limfy.analysis.classification;

import pl.tobynartowski.limfy.model.data.RiskFactorData;
import pl.tobynartowski.limfy.model.persitent.RiskFactor;

import java.util.HashMap;
import java.util.Map;

public class BodyDataClassified {

    private Map<RiskFactor, Double> dataMap = new HashMap<>();

    void addEntry(RiskFactor riskFactor, Double rate) {
        dataMap.put(riskFactor, rate);
    }

    public Map.Entry<RiskFactor, Double> of(RiskFactorData riskFactorData) {
        return dataMap.entrySet()
                .stream()
                .filter(r -> r.getKey().getName().equals(riskFactorData.getName()))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }
}
