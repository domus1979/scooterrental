package by.dvn.scooterrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "by.dvn.scooterrental")
//@EnableAutoConfiguration
public class ScooterRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScooterRentalApplication.class, args);
	}

}
