package backend.daily_quest.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import backend.daily_quest.domain.*;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

@RestController
public class DailyRestController {
    private DailyRepository dailyrepository;
    private StateRepository staterepository;
    private AppUserRepository userrepository;

    public DailyRestController(DailyRepository repository, StateRepository staterepository, AppUserRepository userrepository) {
        this.dailyrepository = repository;
        this.staterepository = staterepository;
        this.userrepository = userrepository;
    }

    // GET ALL, only users own, admin gets all.
    @GetMapping("/api/dailies")
    public List<Daily> getAll(Principal principal) {
        AppUser currentUser = userrepository.findByUsername(principal.getName());

        if (currentUser.getRole().equals("ADMIN")) {
            return (List<Daily>) dailyrepository.findAll();
        } else {
            return dailyrepository.findByAppUser(currentUser);
        }
    }

    // GET BY ID, requires permission to access (owner or admin)
    @GetMapping("/api/dailies/{id}")
    public Daily getById(@PathVariable Long daily_id, Principal principal) {
        Daily daily = dailyrepository.findById(daily_id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AppUser currentUser = userrepository.findByUsername(principal.getName());
        boolean isOwner = daily.getAppUser().getUsername().equals(currentUser.getUsername());
        boolean isAdmin = currentUser.getRole().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return daily;
    }

    // POST DAILY (create new)
    @PostMapping("/api/dailies")
    public Daily createDaily(@RequestBody Daily daily, Principal principal) {
        AppUser currentUser = userrepository.findByUsername(principal.getName());
        daily.setAppUser(currentUser);
        
        Long state_id = daily.getState().getState_id();
    if (state_id == null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "State id missing");
    }

        State state = staterepository.findById(state_id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state id"));
        daily.setState(state);
        return dailyrepository.save(daily);
    }

    // PUT DAILY (update an existing daily)
    @PutMapping("/api/dailies/{id}")
    public Daily updateDaily(@PathVariable Long daily_id, @Valid @RequestBody Daily updatedDaily, Principal principal) {
        Daily existingDaily = dailyrepository.findById(daily_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        AppUser currentUser = userrepository.findByUsername(principal.getName());
        boolean isOwner = existingDaily.getAppUser().getUsername().equals(currentUser.getUsername());
        boolean isAdmin = currentUser.getRole().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        existingDaily.setName(updatedDaily.getName());
        existingDaily.setDescription(updatedDaily.getDescription());
        existingDaily.setStartTime(updatedDaily.getStartTime());
        existingDaily.setEndTime(updatedDaily.getEndTime());
        existingDaily.setCanBeSkipped(updatedDaily.getCanBeSkipped());
        existingDaily.setPenaltyForMissing(updatedDaily.getPenaltyForMissing());

        if (updatedDaily.getState() != null) {
            State state = staterepository.findById(updatedDaily.getState().getState_id())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state id"));
            existingDaily.setState(state);
        }

        return dailyrepository.save(existingDaily);
    }

    // DELETE DAILY (admin or owner required)
    @DeleteMapping("api/dailies/{id}")
    public void deleteDaily(@PathVariable Long daily_id, Principal principal) {
        Daily daily = dailyrepository.findById(daily_id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        AppUser currentUser = userrepository.findByUsername(principal.getName());
        boolean isOwner = daily.getAppUser().getUsername().equals(currentUser.getUsername());
        boolean isAdmin = currentUser.getRole().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        dailyrepository.delete(daily);
    }
}
