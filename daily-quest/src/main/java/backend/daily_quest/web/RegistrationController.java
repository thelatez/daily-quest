package backend.daily_quest.web;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import backend.daily_quest.domain.*;
import backend.daily_quest.WebSecurityConfig;

@Controller
public class RegistrationController {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("form") RegistrationForm form, Model model) {

        String username = form.getUsername();
        String password = form.getPassword();
        String passwordRepeat = form.getPasswordRepeat();

        if (username == null || username.length() < 4 || username.length() > 20) {
            model.addAttribute("error", "Username must be 4-20 characters.");
            return "register";
        }

        if (password == null) {
            model.addAttribute("error", "Password must be 4-50 characters.");
            return "register";
        }

        if (passwordRepeat == null || !password.equals(passwordRepeat)) {
        model.addAttribute("error", "Passwords do not match.");
        return "register";
        }

        if (password.length() < 4 || password.length() > 50) {
        model.addAttribute("error", "Password must be 4–50 characters.");
        return "register";
        }

        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists.");
            return "register";
        }


        AppUser newUser = new AppUser();
        newUser.setUsername(username);
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setRole("USER");
        userRepository.save(newUser);

        return "redirect:/login?registered";
    }
}

