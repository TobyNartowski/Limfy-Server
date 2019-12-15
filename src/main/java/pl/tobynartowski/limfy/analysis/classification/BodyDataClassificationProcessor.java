package pl.tobynartowski.limfy.analysis.classification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.tobynartowski.limfy.exception.UnknownRiskFactor;
import pl.tobynartowski.limfy.model.misc.Gender;
import pl.tobynartowski.limfy.model.persitent.BodyData;
import pl.tobynartowski.limfy.model.persitent.RiskFactor;
import pl.tobynartowski.limfy.repository.RiskFactorRepository;

import java.util.Calendar;

@Service
public class BodyDataClassificationProcessor {

    @Value("${limfy.analysis.average-steps-per-day}")
    private int sampleAverageStepsPerDay;

    @Value("${limfy.analysis.average-heartbeat}")
    private int sampleAverageHeartbeat;

    @Value("${limfy.analysis.optimal-bmi}")
    private int optimalBMI;

    @Value("${limfy.analysis.optimal-age}")
    private int optimalAge;

    private RiskFactorRepository riskFactorRepository;

    @Autowired
    public BodyDataClassificationProcessor(RiskFactorRepository riskFactorRepository) {
        this.riskFactorRepository = riskFactorRepository;
    }

    private Double linearMap(double value, int originalStart, int originalEnd, int desiredStart, int desiredEnd) {
        if (originalEnd <= originalStart) {
            return value;
        }

        if (value <= originalStart) {
            return (double) desiredStart;
        }

        if (value >= originalEnd) {
            return (double) desiredEnd;
        }

        return (value - originalStart) / (originalEnd - originalStart)
                * (desiredEnd - desiredStart) + desiredStart;
    }

    private Double calculateHeartbeatAverageFactor(double heartbeatAverage) {
        return linearMap(heartbeatAverage, sampleAverageHeartbeat - 50, sampleAverageHeartbeat + 50,
                -100, 100);
    }

    private Double calculatePhysicalActivityFactor(double stepsAverage) {
        return linearMap(stepsAverage, sampleAverageStepsPerDay - 2000, sampleAverageStepsPerDay + 2000,
                100, -100);
    }

    private Double calculatePhysicalConditionFactor(double heartbeatAverage, double stepsAverage) {
        return (calculateHeartbeatAverageFactor(heartbeatAverage) * 3 + calculatePhysicalActivityFactor(stepsAverage)) / 2;
    }

    private Double calculateBMIFactor(double bmi) {
        return linearMap(bmi, optimalBMI, 35, -100, 100);
    }

    private Double calculateAgeFactor(int age) {
        return linearMap(age, optimalAge, 100, -100, 100);
    }

    private Double calculateGenderFactor(Gender gender) {
        return gender == Gender.MALE ? 10.0 : -10.0;
    }

    public BodyDataClassified classifyData(double heartbeatAverage, double stepsAverage, BodyData bodyData) {
        BodyDataClassified bodyDataClassified = new BodyDataClassified();

        for (RiskFactor riskFactor : riskFactorRepository.findAll()) {
            switch (riskFactor.of()) {
                case HEARTBEAT_AVERAGE:
                    bodyDataClassified.addEntry(riskFactor, calculateHeartbeatAverageFactor(heartbeatAverage));
                    break;
                case PHYSICAL_ACTIVITY:
                    bodyDataClassified.addEntry(riskFactor, calculatePhysicalActivityFactor(stepsAverage));
                    break;
                case PHYSICAL_CONDITION:
                    bodyDataClassified.addEntry(riskFactor, calculatePhysicalConditionFactor(heartbeatAverage, stepsAverage));
                    break;
                case BMI:
                    bodyDataClassified.addEntry(riskFactor,
                            calculateBMIFactor((double) bodyData.getWeight() / Math.pow(bodyData.getHeight(), 2) * 10_000));
                    break;
                case AGE:
                    bodyDataClassified.addEntry(riskFactor,
                            calculateAgeFactor(Calendar.getInstance().get(Calendar.YEAR) - bodyData.getAge()));
                    break;
                case GENDER:
                    bodyDataClassified.addEntry(riskFactor, calculateGenderFactor(bodyData.getGender()));
                    break;
                default:
                    throw new UnknownRiskFactor();
            }
        }

        return bodyDataClassified;
    }
}
