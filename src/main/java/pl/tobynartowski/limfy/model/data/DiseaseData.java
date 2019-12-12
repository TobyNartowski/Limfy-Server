package pl.tobynartowski.limfy.model.data;

public enum DiseaseData {

    CARDIOVASCULAR_DISEASES(
            RiskFactorData.HEARTBEAT_AVERAGE,
            RiskFactorData.PHYSICAL_ACTIVITY,
            RiskFactorData.PHYSICAL_CONDITION,
            RiskFactorData.BMI,
            RiskFactorData.AGE,
            RiskFactorData.GENDER
    ),
    CANCER(
            RiskFactorData.PHYSICAL_ACTIVITY,
            RiskFactorData.PHYSICAL_CONDITION,
            RiskFactorData.BMI,
            RiskFactorData.AGE
    ),
    TYPE_2_DIABETES(
            RiskFactorData.PHYSICAL_ACTIVITY,
            RiskFactorData.PHYSICAL_CONDITION,
            RiskFactorData.BMI
    ),
    STROKE(
            RiskFactorData.PHYSICAL_ACTIVITY,
            RiskFactorData.PHYSICAL_CONDITION,
            RiskFactorData.BMI,
            RiskFactorData.AGE,
            RiskFactorData.GENDER
    );

    private RiskFactorData[] riskFactors;

    DiseaseData(RiskFactorData... riskFactors) {
        this.riskFactors = riskFactors;
    }

    public RiskFactorData[] getRiskFactors() {
        return riskFactors;
    }
}
