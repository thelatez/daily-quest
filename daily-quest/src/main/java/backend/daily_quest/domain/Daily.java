package backend.daily_quest.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Daily {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long daily_id;

    private String name;
    private String description;
    private LocalDate startTime; // when can be completed or skipped.
    private LocalDate endTime; // when has to be completed or skipped.
    private State state; // Completed OR Skipped OR Unfinished OR Missed.
    private Boolean canBeSkipped; // Can it be skipped?
    private Boolean penaltyForMissing; // Does some penalty occur if missed (or skipped?)

    public Daily() {
    }

    public Daily(String name, String description, LocalDate startTime, LocalDate endTime, State state, Boolean canBeSkipped, Boolean penaltyForMissing) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = state;
        this.canBeSkipped = canBeSkipped;
        this.penaltyForMissing = penaltyForMissing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public Long getDaily_id() {
        return daily_id;
    }

    public void setDaily_id(Long daily_id) {
        this.daily_id = daily_id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Boolean getCanBeSkipped() {
        return canBeSkipped;
    }

    public void setCanBeSkipped(Boolean canBeSkipped) {
        this.canBeSkipped = canBeSkipped;
    }

    public Boolean getPenaltyForMissing() {
        return penaltyForMissing;
    }

    public void setPenaltyForMissing(Boolean penaltyForMissing) {
        this.penaltyForMissing = penaltyForMissing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
