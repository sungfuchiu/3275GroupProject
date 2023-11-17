package com.example.techassist.Web;

import com.example.techassist.Entities.Technician;
import com.example.techassist.Entities.User;
import com.example.techassist.Repositories.TechnicianRepository;
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
                User loggedInUser = user.get();
                model.addAttribute("user", loggedInUser);

                // Redirect based on userType
                if ("Customer".equals(loggedInUser.getUserType())) {
                    return "redirect:/mainLogin.html";
                } else if ("Technician".equals(loggedInUser.getUserType())) {
                    return "redirect:/main.html";
                } else {
                    // Handle other user types as needed
                    return "redirect:/mainLogin.html";
                }
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

    /*

    @PostMapping("/createaccount")
    public String createAccount(@RequestParam("createfullname") String fullName,
                                @RequestParam("createemail") String email,
                                @RequestParam("createusername") String username,
                                @RequestParam("createpassword") String password,
                                @RequestParam("userType") String userType,
                                Model model) {
        try {
            // Create a new User object and set its properties
            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setUserType(userType);

            // Save the user to the database
            userRepository.save(newUser);

            // Retrieve the saved user from the database to get the generated ID
            User savedUser = userRepository.findById(newUser.getId()).orElseThrow();

            // Add the user to the model
            model.addAttribute("user", savedUser);

            // Redirect based on userType
            if ("Customer".equals(userType)) {
                return "redirect:/mainLogin.html";
            } else if ("Technician".equals(userType)) {
                return "redirect:/main.html";
            } else {
                // Handle other user types as needed
                return "redirect:/mainLogin.html";
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();

            // Add the exception message to the model for display on the createaccount page
            model.addAttribute("error", "An unexpected error occurred while creating the account: " + e.getMessage());

            // Return to the createaccount page with an error message
            return "createaccount";
        }
    }

     */
    @PostMapping("/createaccount")
    public String createAccount(@RequestParam("createfullname") String fullName,
                                @RequestParam("createemail") String email,
                                @RequestParam("createusername") String username,
                                @RequestParam("createpassword") String password,
                                @RequestParam("userType") String userType,
                                Model model) {
        try {
            // Check if the username already exists
            Optional<User> existingUser = userRepository.findByUsername(username);

            if (existingUser.isPresent()) {
                // Username already exists, redirect to login page
                model.addAttribute("error", "Username already exists. Please choose a different username or go to login.");
                return "createaccount";
            }

            // Create a new User object and set its properties
            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setUserType(userType);

            // Save the user to the database
            userRepository.save(newUser);

            // Retrieve the saved user from the database to get the generated ID
            User savedUser = userRepository.findById(newUser.getId()).orElseThrow();

            // Add the user to the model
            model.addAttribute("user", savedUser);

            // Redirect based on userType
            if ("Customer".equals(userType)) {
                return "redirect:/mainLogin.html";
            } else if ("Technician".equals(userType)) {
                return "redirect:/main.html";
            } else {
                // Handle other user types as needed
                return "redirect:/mainLogin.html";
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();

            // Add the exception message to the model for display on the createaccount page
            model.addAttribute("error", "An unexpected error occurred while creating the account: " + e.getMessage());

            // Return to the createaccount page with an error message
            return "createaccount";
        }
    }


    @Autowired
    private TechnicianRepository technicianRepository;

    @GetMapping("/technicianinfo/{technicianId}")
    public String getTechnicianInfo(@PathVariable Long technicianId, Model model) {
        Optional<Technician> technicianOptional = technicianRepository.findById(technicianId);

        if (technicianOptional.isPresent()) {
            Technician technician = technicianOptional.get();
            model.addAttribute("technician", technician);
            return "technicianinfo"; // Assuming your template is named "technicianinfo.html"
        } else {
            // Handle the case where the technician with the given ID is not found
            return "error"; // You can create an error Thymeleaf template
        }
    }



}



