package pl.tobynartowski.limfy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.tobynartowski.limfy.analysis.data.DataIntegrityManager;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class LimfyServerApplication {

    private DataIntegrityManager dataIntegrityManager;

    @Autowired
    public LimfyServerApplication(DataIntegrityManager dataIntegrityManager) {
        this.dataIntegrityManager = dataIntegrityManager;
    }

    @PostConstruct
    private void init() {
        dataIntegrityManager.checkDatabaseData();
    }

    public static void main(String[] args) {
        SpringApplication.run(LimfyServerApplication.class, args);
    }
}