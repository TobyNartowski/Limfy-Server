package pl.tobynartowski.limfy.exception;

public class UnknownRiskFactor extends RuntimeException {

    private static final long serialVersionUID = -3982835875314283127L;

    @Override
    public String getMessage() {
        return "Classification processor has not found appropriate calculation method";
    }
}
