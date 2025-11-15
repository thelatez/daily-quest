package backend.daily_quest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import backend.daily_quest.domain.*;
import java.time.LocalTime;

@SpringBootApplication
public class DailyQuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyQuestApplication.class, args);
	}


	@Bean
	public CommandLineRunner demo(DailyRepository dailyrepository, StateRepository staterepository, AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {

			AppUser admin = userRepository.findByUsername("admin");
			if (admin == null) {
				admin = new AppUser();
				admin.setUsername("admin");
				admin.setPasswordHash(passwordEncoder.encode("admin"));
				admin.setRole("ADMIN");
				userRepository.save(admin);
			}

			AppUser user = userRepository.findByUsername("user");
			if (user == null) {
				user = new AppUser();
				user.setUsername("user");
				user.setPasswordHash(passwordEncoder.encode("user"));
				user.setRole("USER");
				userRepository.save(user);
			}

			AppUser user2 = userRepository.findByUsername("user2");
			if (user2 == null) {
				user2 = new AppUser();
				user2.setUsername("user2");
				user2.setPasswordHash(passwordEncoder.encode("user2"));
				user2.setRole("USER");
				userRepository.save(user2);
			}

			State unfinished = staterepository.findByName("Unfinished")
				.orElseGet(() -> staterepository.save(new State("Unfinished")));
			State completed = staterepository.findByName("Completed")
				.orElseGet(() -> staterepository.save(new State("Completed")));
			State missed = staterepository.findByName("Missed")
				.orElseGet(() -> staterepository.save(new State("Missed")));

			dailyrepository.save(new Daily(user, "Brush teeth", "Scrub scrub", LocalTime.of(1, 0), LocalTime.of(11, 0), unfinished));
			dailyrepository.save(new Daily(user, "Take medication", "Magnesium, B-vitamin", LocalTime.of(1, 0), LocalTime.of(12, 0), unfinished));

		};
	};
}; 
