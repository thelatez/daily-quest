package backend.daily_quest.domain;

//import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.CascadeType;
//import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long state_id;

    private String name;
    
    @JsonIgnoreProperties("state")
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "state")
    //private List<Daily> dailies;

    public State() {
    }
    
    public State(String name) {
        this.name = name;
    }

    public Long getState_id() {
        return state_id;
    }

    public void setState_id(Long state_id) {
        this.state_id = state_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public List<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(List<Daily> dailies) {
        this.dailies = dailies;
    }*/
}

