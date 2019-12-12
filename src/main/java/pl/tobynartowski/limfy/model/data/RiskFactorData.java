package pl.tobynartowski.limfy.model.data;

public enum RiskFactorData {

    HEARTBEAT_AVERAGE("Średnie tętno"),
    PHYSICAL_ACTIVITY("Aktywność fizyczna"),
    PHYSICAL_CONDITION("Kondycja fizyczna"),
    BMI("BMI"),
    AGE("Wiek"),
    GENDER("Płeć");

    private String name;

    RiskFactorData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
