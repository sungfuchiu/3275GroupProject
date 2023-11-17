package com.example.techassist.Web;

import com.example.techassist.Entities.Technician;
import com.example.techassist.Entities.User;
import com.example.techassist.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.techassist.Entities.User;
import com.example.techassist.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SessionAttributes("user")
@Controller
@AllArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(path = "/history.html")
    public String history() {
        return "history";
    }

    @GetMapping(path = "/login.html")
    public String login() {
        return "login";
    }


    @GetMapping(path = "/mainTomoya.html")
    public String mainTomoya() {
        return "mainTomoya";
    }

    @GetMapping(path = "/createaccount.html")
    public String createaccount() {
        return "createaccount";
    }

    @GetMapping(path = "/technicianinfo.html")
    public String technicianinfo() {
        return "technicianinfo";
    }

    @GetMapping(path = "/confirmation.html")
    public String confirmation() {
        return "confirmation";
    }

    @GetMapping(path = "/payment.html")
    public String payment() {
        return "payment";
    }

    @GetMapping(path = "/complete.html")
    public String complete() {
        return "complete";
    }

    @GetMapping(path = "/main.html")
    public String main() {
        return "main";
    }

    @GetMapping(path = "/mainLogin.html")
    public String mainLogin() {
        return "mainLogin";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("loginusername") String username,
                            @RequestParam("loginpassword") String password,
                            @RequestParam("userType") String userType,
                            Model model) {
        try {
            System.out.println("Received parameters - username: " + username + ", password: " + password + ", userType: " + userType);

            Optional<User> user = userRepository.findByUsernameAndPasswordAndUserType(username, password, userType);

            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                return "redirect:/mainLogin.html";
            } else {
                model.addAttribute("error", "Invalid username, password, or user type");
                return "login";
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            model.addAttribute("error", "An unexpected error occurred");
            return "login";
        }
    }


}



