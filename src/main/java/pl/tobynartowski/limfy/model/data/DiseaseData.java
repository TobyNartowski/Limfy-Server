package pl.tobynartowski.limfy.model.data;

public enum DiseaseData {

    CARDIOVASCULAR_DISEASES(
            "Choroby sercowo-naczyniowe", 31,
            RiskFactorData.HEARTBEAT_AVERAGE,
            RiskFactorData.PHYSICAL_ACTIVITY,
            RiskFactorData.PHYSICAL_CONDITION,
            RiskFactorData.BMI,
            RiskFactorData.AGE,
            RiskFactorData.GENDER
    ),
    CANCER(
            "Nowotwory", 17,
            RiskFactorData.PHYSICAL_ACTIVITY,
            RiskFactorData.PHYSICAL_CONDITION,
            RiskFactorData.BMI,
            RiskFactorData.AGE
    ),
    TYPE_2_DIABETES(
            "Cukrzyca typu 2", 15,
            RiskFactorData.PHYSICAL_ACTIVITY,
            RiskFactorData.PHYSICAL_CONDITION,
            RiskFactorData.BMI
    ),
    STROKE(
            "Udar", 5,
            RiskFactorData.PHYSICAL_ACTIVITY,
            RiskFactorData.PHYSICAL_CONDITION,
            RiskFactorData.BMI,
            RiskFactorData.AGE,
            RiskFactorData.GENDER
    );

    private String name;
    private RiskFactorData[] riskFactors;
    private int healthInfluence;

    DiseaseData(String name, int healthInfluence, RiskFactorData... riskFactors) {
        this.name = name;
        this.riskFactors = riskFactors;
        this.healthInfluence = healthInfluence;
    }

    public RiskFactorData[] getRiskFactors() {
        return riskFactors;
    }

    public String getName() {
        return name;
    }

    public int getHealthInfluence() {
        return healthInfluence;
    }
}
