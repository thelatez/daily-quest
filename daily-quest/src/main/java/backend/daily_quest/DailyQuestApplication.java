package backend.daily_quest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.daily_quest.domain.*;

@SpringBootApplication
public class DailyQuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyQuestApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(DailyRepository dailyrepository, StateRepository staterepository) {
		return (args) -> {
		};
	}; 
}
