package rs.raf.domaci_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Domaci_3Application {

    public static void main(String[] args) {
        SpringApplication.run(Domaci_3Application.class, args);
    }

}
