package backend.daily_quest.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.*;

@Entity
public class Daily {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long daily_id;

    @ManyToOne
    @JoinColumn(name = "appUser_id")
    private AppUser appUser;

    @NotBlank(message = "Action must not be empty")
    @Size(min = 1, max = 50, message = "Action must be  1-50 characters")
    private String name; // what to do, ex. brush teeth. 

    @Size(min = 0, max = 300, message = "Description can be at most 300 characters")
    private String description;

    @NotNull(message = "Start time required")
    private String startTime; // when can be completed or skipped.

    @NotNull(message = "End time required")
    private String endTime; // when has to be completed or skipped.

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state; // Completed OR Skipped OR Unfinished OR Missed.
    private Boolean canBeSkipped; // Can it be skipped?
    private Boolean penaltyForMissing; // Does some penalty occur if missed (or skipped?)

    public Daily() {
    }

    public Daily(AppUser appUser, String name, String description, String startTime, String endTime, State state, Boolean canBeSkipped, Boolean penaltyForMissing) {
        this.appUser = appUser;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
