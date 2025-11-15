package backend.daily_quest.web;

import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import backend.daily_quest.domain.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.ArrayList;

@Controller
public class DailyController {
    private DailyRepository dailyrepository;
    private StateRepository staterepository;
    private AppUserRepository userrepository;

    public DailyController(DailyRepository repository, StateRepository staterepository, AppUserRepository userrepository) {
        this.dailyrepository = repository;
        this.staterepository = staterepository;
        this.userrepository = userrepository;
    }

    @GetMapping({"/dailylist"})
    public String dailyList(Model model, Principal principal) {
        AppUser currentUser = userrepository.findByUsername(principal.getName());
        
        List<Daily> dailies;

        if (currentUser.getRole().equals("ADMIN")) {
            dailies = new ArrayList<>();
            dailyrepository.findAll().forEach(dailies::add);
        } else {
            dailies = dailyrepository.findByAppUser(currentUser);
        }

        long completedCount = dailies.stream()
            .filter(d -> d.getState().getName().equals("Completed"))
            .count();
        long missedCount = dailies.stream()
            .filter(d -> d.getState().getName().equals("Missed"))
            .count();

        model.addAttribute("dailies", dailies);
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("completedCount", completedCount);
        model.addAttribute("missedCount", missedCount);
        return "dailylist";
    }

    @GetMapping("/newdaily")
    public String newDaily(Model model) {
        Daily daily = new Daily();

        State defaultState = staterepository.findByName("Unfinished").orElse(null);
        daily.setState(defaultState);

        daily.setCanBeSkipped(false);
        daily.setPenaltyForMissing(false);

        model.addAttribute("daily", daily); // <-- use the same instance
        model.addAttribute("states", staterepository.findAll());
        return "newdaily";
    }


    @PostMapping("/save")
    public String saveDaily(@Valid Daily daily, BindingResult binding, Principal principal) {
        if (binding.hasErrors()) {
            binding.getAllErrors().forEach(System.out::println);
            return "newdaily";
        }
        AppUser currentUser = userrepository.findByUsername(principal.getName());
        daily.setAppUser(currentUser);

        if (daily.getState() != null && daily.getState().getState_id() != null) {
            State state = staterepository.findById(daily.getState().getState_id())
                .orElseThrow(() -> new RuntimeException("State not found"));
            daily.setState(state);
        } else {
            throw new RuntimeException("State must be selected");
        }
        if (daily.getCanBeSkipped() == null) daily.setCanBeSkipped(false);
        if (daily.getPenaltyForMissing() == null) daily.setPenaltyForMissing(false);

        dailyrepository.save(daily);
        return "redirect:/dailylist";
    }

    @PostMapping("/update")
    public String updateDaily(@Valid Daily updatedDaily, BindingResult binding, Principal principal) {
        if (binding.hasErrors()) {
            binding.getAllErrors().forEach(System.out::println);
            return "editdaily";
        }

        Daily current = dailyrepository.findById(updatedDaily.getDaily_id())
                .orElseThrow(() -> new RuntimeException("Daily not found"));

        AppUser currentUser = userrepository.findByUsername(principal.getName());

        if (!currentUser.getRole().equals("ADMIN") && 
            !current.getAppUser().getUsername().equals(currentUser.getUsername())) {
            return "redirect:/access-denied";
        }

        current.setName(updatedDaily.getName());
        current.setDescription(updatedDaily.getDescription());
        current.setStartTime(updatedDaily.getStartTime());
        current.setEndTime(updatedDaily.getEndTime());
        current.setCanBeSkipped(updatedDaily.getCanBeSkipped());
        current.setPenaltyForMissing(updatedDaily.getPenaltyForMissing());

        if (updatedDaily.getState() != null && updatedDaily.getState().getState_id() != null) {
            State state = staterepository.findById(updatedDaily.getState().getState_id())
                .orElseThrow(() -> new RuntimeException("Invalid state_id"));
            current.setState(state);
        }
        dailyrepository.save(current);
        return "redirect:/dailylist";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/delete/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDaily(@PathVariable("id") Long daily_id, Model model, Principal principal) {
        Daily daily = dailyrepository.findById(daily_id)
            .orElseThrow(() -> new RuntimeException("Daily not found"));

        AppUser currentUser = userrepository.findByUsername(principal.getName());
        boolean isOwner = daily.getAppUser().getUsername().equals(currentUser.getUsername());
        boolean isAdmin = currentUser.getRole().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            return "redirect:/access-denied";
        }
        
        dailyrepository.deleteById(daily_id);
        return "redirect:../dailylist";
    }

    @GetMapping("/edit/{id}")
    public String editDaily(@PathVariable("id") Long daily_id, Model model, Principal principal) {
        Daily daily = dailyrepository.findById(daily_id)
            .orElseThrow(() -> new RuntimeException("Daily not found"));

        AppUser currentUser = userrepository.findByUsername(principal.getName());
        boolean isOwner = daily.getAppUser().getUsername().equals(currentUser.getUsername());
        boolean isAdmin = currentUser.getRole().equals("ADMIN");

        if (!isOwner && !isAdmin) {
            return "redirect:/access-denied";
        }

        model.addAttribute("daily", daily);
        model.addAttribute("states", staterepository.findAll());
    return "editdaily";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

}
