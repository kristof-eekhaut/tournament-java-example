package be.eekhaut.kristof.tournament;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class TournamentApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentApplication.class, args);
	}

}
