package backend.daily_quest.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import backend.daily_quest.domain.*;
import jakarta.validation.Valid;

import java.security.Principal;
import java.util.List;

@Controller
public class RestController {
    private DailyRepository dailyrepository;
    private StateRepository staterepository;
    private AppUserRepository userrepository;

    public RestController(DailyRepository repository, StateRepository staterepository, AppUserRepository userrepository) {
        this.dailyrepository = repository;
        this.staterepository = staterepository;
        this.userrepository = userrepository;
    }

    @GetMapping("/api/dailies")
    public @ResponseBody List<Daily> getUserDailies(Principal principal) {
        AppUser user = userrepository.findByUsername(principal.getName());

        if (user.getRole().equals("ADMIN")) {
            return (List<Daily>) dailyrepository.findAll();
        } else {
            return dailyrepository.findByAppUser(user);
        }
    }

    @GetMapping("/api/dailies/{id}")
public ResponseEntity<?> getDailyById(@PathVariable Long id, Principal principal) {
    Daily daily = dailyrepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    AppUser currentUser = userrepository.findByUsername(principal.getName());

    boolean isOwner = daily.getAppUser().getUsername().equals(currentUser.getUsername());
    boolean isAdmin = currentUser.getRole().equals("ADMIN");

    if (!isOwner && !isAdmin) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    return ResponseEntity.ok(daily);
}
    

    @PostMapping("/api/dailies")
    public ResponseEntity<?> createDaily(@Valid @RequestBody Daily daily, BindingResult binding, Principal principal) {
        if (binding.hasErrors()) {
            return ResponseEntity.badRequest().body(binding.getAllErrors());
        }

        AppUser user = userrepository.findByUsername(principal.getName());
        daily.setAppUser(user);
        Daily saved = dailyrepository.save(daily);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/api/dailies/{id}")
    public ResponseEntity<?> updateDaily(@PathVariable Long id, @Valid @RequestBody Daily updated, Principal principal) {
        AppUser user = userrepository.findByUsername(principal.getName());
        Daily existing = dailyrepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!user.getRole().equals("ADMIN") && !existing.getAppUser().equals(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setStartTime(updated.getStartTime());
        existing.setEndTime(updated.getEndTime());

        dailyrepository.save(existing);
        return ResponseEntity.ok(existing);
    }

}
