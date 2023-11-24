package com.example.techassist.Web;

import com.example.techassist.Entities.Technician;
import com.example.techassist.Entities.User;
import com.example.techassist.Repositories.TechnicianRepository;
import com.example.techassist.Repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;

import java.util.Optional;

@SessionAttributes("user")
@Controller
@AllArgsConstructor
public class UserController {

    private final HttpSession httpSession;
    @Autowired
    private UserRepository userRepository;


//    @GetMapping(path = "/history.html")
//    public String history() {
//        return "history";
//    }

//    @GetMapping(path = "/login")
//    public String login() {
//        return "user/login";
//    }


    @GetMapping(path = "/mainTomoya.html")
    public String mainTomoya() {
        return "mainTomoya";
    }

    @GetMapping(path = "/register")
    public String register() {
        return "user/register";
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

            Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

            if (user.isPresent()) {
                User loggedInUser = user.get();
                model.addAttribute("user", loggedInUser);
                httpSession.setAttribute("username", username);//Add username session for other page to use

                // Redirect based on userType
                if (loggedInUser.getClient() != null) {
                    return "redirect:/clientHome";
                } else if (loggedInUser.getTechnician() != null) {
                    return "redirect:/technicianHome";
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
            return "user/login";
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
    @PostMapping("/register")
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
                return "user/register";
            }

            // Create a new User object and set its properties
            User newUser = new User();
            newUser.setName(fullName);
            newUser.setUsername(email);
            newUser.setPassword(password);

            // Save the user to the database
            userRepository.save(newUser);

            // Retrieve the saved user from the database to get the generated ID
            User savedUser = userRepository.findByUsername(newUser.getUsername()).orElseThrow();

            // Add the user to the model
            model.addAttribute("user", savedUser);
            httpSession.setAttribute("username", username);//Add username session for other page to use

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
            return "user/register";
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
            return "technicianinfo"; // Assuming your template is named "technicianInfo.html"
        } else {
            // Handle the case where the technician with the given ID is not found
            return "error"; // You can create an error Thymeleaf template
        }
    }



}



