package backend.daily_quest.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Daily {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long daily_id;

    private String name;
    private String startTime; // when can be completed or skipped.
    private String endTime; // when has to be completed or skipped.
    private Category category; // ex. Home OR Work OR School etc.
    private String state; // Completed OR Skipped OR Unfinished OR Missed.
    private Boolean canBeSkipped; // Can it be skipped?
    private Boolean penaltyForMissing; // Does some penalty occur if missed (or skipped?)

    public Daily() {
    }

    public Daily(String name, String startTime, String endTime, Category category, String state, Boolean canBeSkipped, Boolean penaltyForMissing) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
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
}
