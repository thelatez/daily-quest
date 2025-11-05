package backend.daily_quest.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import backend.daily_quest.domain.*;

import java.util.List;
import java.util.Optional;

@Controller
public class RestController {
    private DailyRepository dailyrepository;
    private StateRepository staterepository;

    public RestController(DailyRepository repository, StateRepository staterepository) {
        this.dailyrepository = repository;
        this.staterepository = staterepository;
    }

    @GetMapping(value="/api/dailies")
    public @ResponseBody List<Daily> dailiesRest() {
        return (List<Daily>) dailyrepository.findAll();
    }

    @GetMapping(value="/dailies/{id}")
    public @ResponseBody Optional<Daily> findDailyRest(@PathVariable("id") Long daily_id) {	
        return dailyrepository.findById(daily_id);
    }       

    @PostMapping(value="/api/dailies")
    public @ResponseBody Daily newDailyRest(@RequestBody Daily daily) {	
        return dailyrepository.save(daily);
    }
}
