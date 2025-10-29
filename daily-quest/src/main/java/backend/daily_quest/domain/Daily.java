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
    private Category category;

    public Daily() {
    }

    public Daily(Category category) {
        this.category = category;
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



}
