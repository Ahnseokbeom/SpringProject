package song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MelonApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MelonApiApplication.class, args);
	}

}
