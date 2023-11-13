package com.example.groupprojecttechnician.Web;

import com.example.groupprojecttechnician.Entities.SetTR;
import com.example.groupprojecttechnician.Entities.Technician;
import com.example.groupprojecttechnician.Repositories.SetTRRepository;
import com.example.groupprojecttechnician.Repositories.TechnicianRepository;
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
            // Redirect to the main page or another page
            return "redirect:/index";
        }
    }
}
