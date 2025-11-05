package backend.daily_quest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.daily_quest.domain.*;

import java.util.List;
import java.time.LocalDate;

@SpringBootApplication
public class DailyQuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyQuestApplication.class, args);
	}

	State unfinished = new State("Unfinished");
	State skipped = new State("Skipped");
	State completed = new State("Completed");
	State missed = new State("Missed");

	List<State> statelist = List.of(unfinished, skipped, completed, missed);

	@Bean
	public CommandLineRunner demo(DailyRepository dailyrepository, StateRepository staterepository) {
		return (args) -> {
			staterepository.saveAll(statelist);

			dailyrepository.save(new Daily("Brush teeth", "", LocalDate.now(), LocalDate.now(), unfinished, false, true));
			dailyrepository.save(new Daily("Take medication", "", LocalDate.now(), LocalDate.now(), unfinished, true, true));

			//AppUser user1 = new AppUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "user@user.com", "USER");
			//AppUser admin = new AppUser("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C","admin@admin.com", "ADMIN");
			//userRepository.save(user1);
			//userRepository.save(admin);
		};
	};
}; 
