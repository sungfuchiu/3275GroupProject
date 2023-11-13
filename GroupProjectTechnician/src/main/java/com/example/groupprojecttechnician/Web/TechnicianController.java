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

public class TechnicianController {

    @Autowired
    private TechnicianRepository technicianRepository;

    static int num = 0;

    @GetMapping(path = "/index")
    public String index() {
        return "main";
    }

    @GetMapping(path = "/technicianProfile.html")
    public String technicianProfile() {
        return "technicianProfile";
    }

    @PostMapping(path = "/save")
    public String save(@ModelAttribute Technician technician, BindingResult bindingResult, ModelMap mm) {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            mm.addAttribute("technician", technician);
            return "technicianProfile";
        } else {
            // Save the technician data to the database
            technicianRepository.save(technician);
            // Redirect to the main page
            return "redirect:/index";
        }
    }


//    @GetMapping(path = "/setTimeRate.html")
//    public String setTimeRate() {
//        return "setTimeRate";
//    }


//    @PostMapping(path = "/saveTR")
//    public String saveTR(SetTR setTR, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            // Handle validation errors
//            return "setTimeRate";
//        } else {
//            // Save the data to the database
//            setTRRepository.save(setTR);
//            // Redirect to the main page or another page
//            return "redirect:/index";
//        }
//    }


    @GetMapping(path = "/viewAppointment.html")
    public String viewAppointment() {
        return "viewAppointment";
    }

    @GetMapping(path = "/accountBalance.html")
    public String accountBalance() {
        return "accountBalance";
    }

    @GetMapping(path = "/history.html")
    public String history() {
        return "history";
    }

}
