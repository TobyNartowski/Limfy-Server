package pl.tobynartowski.limfy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.tobynartowski.limfy.analysis.AnalyticalDataManager;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LimfyServerApplication {

    private AnalyticalDataManager analyticalDataManager;

    @Autowired
    public LimfyServerApplication(AnalyticalDataManager analyticalDataManager) {
        this.analyticalDataManager = analyticalDataManager;
    }

    @PostConstruct
    private void init() {
        analyticalDataManager.checkDatabaseIntegrity();
    }

    public static void main(String[] args) {
        SpringApplication.run(LimfyServerApplication.class, args);
    }
}