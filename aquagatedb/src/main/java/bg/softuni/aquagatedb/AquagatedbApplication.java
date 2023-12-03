package bg.softuni.aquagatedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AquagatedbApplication {

    public static void main(String[] args) {
        SpringApplication.run(AquagatedbApplication.class, args);
    }

}
