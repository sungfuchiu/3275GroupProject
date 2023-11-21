package com.example.techassist.Web;

import com.example.techassist.Entities.SetTR;
import com.example.techassist.Entities.Technician;
import com.example.techassist.Repositories.SetTRRepository;
import com.example.techassist.Repositories.TechnicianRepository;
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

    @Autowired
    private SetTRRepository setTRRepository;

    @GetMapping(path = "/setTimeRate.html")
    public String setTimeRate() {
        return "setTimeRate";
    }


    @PostMapping(path = "/saveTR")
    public String saveTR(@ModelAttribute SetTR setTR, BindingResult bindingResult, ModelMap mm) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            mm.addAttribute("setTR", setTR);
            return "setTimeRate";
        } else {
            // Save the data to the database
            setTRRepository.save(setTR);
            mm.addAttribute("successMessage", "Data successfully saved");
            // Redirect to the main page or another page
            return "setTimeRate";
        }
    }
}
