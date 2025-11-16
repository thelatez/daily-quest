package backend.daily_quest.config;

import backend.daily_quest.domain.Daily;
import backend.daily_quest.domain.State;
import backend.daily_quest.domain.DailyRepository;
import backend.daily_quest.domain.StateRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class DailyScheduler {

    private DailyRepository dailyrepository;
    private StateRepository staterepository;

    public DailyScheduler(DailyRepository dailyrepository, StateRepository staterepository) {
        this.dailyrepository = dailyrepository;
        this.staterepository = staterepository;
    }

    @Scheduled(fixedRate = 60_000, zone = "Europe/Helsinki") // 60 seconds
    public void markMissedDailies() {
        System.out.println("Scheduler running at: " + LocalDateTime.now());
        Iterable<Daily> dailies = dailyrepository.findAll();
        State missedState = staterepository.findByName("Missed").orElse(null);
        State completedState = staterepository.findByName("Completed").orElse(null);

        if (missedState == null || completedState == null) {
            return;
        }

        LocalTime now = LocalTime.now();

        for (Daily daily : dailies) {
            if (daily.getState().getName().equals(completedState.getName())) {
                continue;
            } 

            if (daily.getEndTime() != null && now.isAfter(daily.getEndTime())) {
                if (!daily.getState().getName().equals(missedState.getName())) {
                    daily.setState(missedState);
                    dailyrepository.save(daily);
                    System.out.println("Marked daily as missed: " + daily.getName());
                }
            }
        }
    }
}
