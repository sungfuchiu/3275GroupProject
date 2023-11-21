package com.example.techassist.Web;

//import com.example.techassist.Entities.SetTR;
import com.example.techassist.Entities.Technician;
//import com.example.techassist.Repositories.SetTRRepository;
import com.example.techassist.Repositories.TechnicianRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes
@Controller
@AllArgsConstructor

public class TechnicianController {

    @Autowired
    private TechnicianRepository technicianRepository;

    static int num = 0;

    @GetMapping(path = "/index")
    public String index() {
        return "mainTechnician";
    }

    @GetMapping(path = "/technicianProfile.html")
    public String technicianProfile() {
        return "technicianProfile";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute Technician technician, BindingResult bindingResult, ModelMap mm) {
        // Check for duplicate email
        if (technicianRepository.existsByEmail(technician.getEmail())) {
            mm.addAttribute("duplicateError", "Email already exists");
            mm.addAttribute("technician", technician);
            return "technicianProfile";
        }

        if (bindingResult.hasErrors()) {
            // Handle validation errors
            mm.addAttribute("technician", technician);
            return "technicianProfile";
        } else {
            // Save the technician data to the database
            technicianRepository.save(technician);
            mm.addAttribute("successMessage", "Data successfully saved");
            // Redirect to the main page
            return "technicianProfile";
        }
    }

    @GetMapping(path = "/viewAppointment.html")
    public String viewAppointment() {
        return "viewAppointment";
    }

    @GetMapping(path = "/accountBalance.html")
    public String accountBalance() {
        return "accountBalance";
    }

    @GetMapping(path = "/history.html")
    public String showhistory(Model model) {
        List<Technician> listTechnicians = technicianRepository.findAll();
        model.addAttribute("listTechnicians", listTechnicians);
        return "history";
    }


}

