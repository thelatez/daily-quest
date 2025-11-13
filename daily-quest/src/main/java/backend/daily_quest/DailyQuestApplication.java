package backend.daily_quest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.daily_quest.domain.*;

import java.util.List;

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
	List<Daily> userlist;
	List<Daily> userlist2;
	List<Daily> adminlist;

	@Bean
	public CommandLineRunner demo(DailyRepository dailyrepository, StateRepository staterepository, AppUserRepository userRepository) {
		return (args) -> {
			staterepository.saveAll(statelist);

			
			AppUser USER = new AppUser("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER", userlist);
			AppUser USER2 = new AppUser("user2", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER", userlist2);
			AppUser ADMIN = new AppUser("admin", "$2a$10$0MMwY.IQqpsVc1jC8u7IJ.2rT8b0Cd3b3sfIBGV2zfgnPGtT4r0.C", "ADMIN", adminlist);
			userRepository.save(USER);
			userRepository.save(USER2);
			userRepository.save(ADMIN);
			

			dailyrepository.save(new Daily(USER, "Brush teeth", "", "", "", unfinished, false, true));
			dailyrepository.save(new Daily(USER2, "Take medication", "", "", "", unfinished, true, true));

		};
	};
}; 
