package com.example.techassist.Web;

import com.example.techassist.Entities.Technician;
import com.example.techassist.Repositories.TechnicianRepository;
import com.example.techassist.Repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@SessionAttributes
@Controller
@AllArgsConstructor
public class SetTRController {

    private final HttpSession httpSession;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/setTimeRate.html")
    public String setTimeRate(Model model) {
        String username = (String) httpSession.getAttribute("username");
        var user = userRepository.findByUsername(username).orElse(null);
        var technician = user.getTechnician();
        model.addAttribute("setTR", technician);
        return "setTimeRate";
    }


    @PostMapping(path = "/saveTR")
    public String saveTR(@ModelAttribute Technician technician, BindingResult bindingResult, ModelMap mm) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            mm.addAttribute("setTR", technician);
            return "setTimeRate";
        } else {
            // Save the data to the database
            technicianRepository.save(technician);
            mm.addAttribute("successMessage", "Data successfully saved");
            // Redirect to the main page or another page
            return "setTimeRate";
        }
    }
}
