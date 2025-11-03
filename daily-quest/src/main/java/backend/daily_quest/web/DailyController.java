package backend.daily_quest.web;

import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import backend.daily_quest.domain.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DailyController {
    private DailyRepository dailyrepository;
    private CategoryRepository categoryrepository;

    public DailyController(DailyRepository repository, CategoryRepository categoryrepository) {
        this.dailyrepository = repository;
        this.categoryrepository = categoryrepository;
    }

    @RequestMapping(value={"/", "/dailylist"})
    public String dailyList(Model model) {
        model.addAttribute("dailies", dailyrepository.findAll());
        return "dailylist";
    }

    @RequestMapping(value = "/newdaily")
    public String newDaily(Model model) {
        model.addAttribute("daily", new Daily());
        model.addAttribute("categories", categoryrepository.findAll());
        return "newdaily";
    }

    @RequestMapping(value = "/savedaily", method = RequestMethod.POST)
    public String saveDaily(Daily Daily) {
        dailyrepository.save(Daily);
        return "redirect:dailylist";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value="/delete/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public String deleteDaily(@PathVariable("id") Long daily_id, Model model) {
        dailyrepository.deleteById(daily_id);
        return "redirect:../dailylist";
    }

    @RequestMapping(value="/edit/{id}")
    public String editDaily(@PathVariable("id") Long daily_id, Model model, Daily Daily) {
        Daily.setDaily_id(daily_id);
        model.addAttribute("daily", dailyrepository.findById(daily_id));
        model.addAttribute("categories", categoryrepository.findAll());
        return "editdaily";
    }

    //REST service to get all dailies.
    @RequestMapping(value="/dailies")
    public @ResponseBody List<Daily> dailyListRest() {
        return (List<Daily>) dailyrepository.findAll();
    }

    // REST service to get Daily via daily_id.
    @RequestMapping(value="/dailies/{id}")
    public @ResponseBody Optional<Daily> findDailyRest(@PathVariable("id") Long daily_id) {	
        return dailyrepository.findById(daily_id);
    }       
    
    // REST service to save new daily
    @RequestMapping(value="/dailies", method = RequestMethod.POST)
    public @ResponseBody Daily saveNewDailyRest(@RequestBody Daily daily) {	
        return dailyrepository.save(daily);
    }
}
