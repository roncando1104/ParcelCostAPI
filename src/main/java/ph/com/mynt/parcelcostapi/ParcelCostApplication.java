package ph.com.mynt.parcelcostapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class ParcelCostApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParcelCostApplication.class, args);
    }
}
