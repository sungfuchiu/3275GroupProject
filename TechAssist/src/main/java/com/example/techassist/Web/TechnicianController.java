package com.example.techassist.Web;

import com.example.techassist.DTO.AccountBalanceDTO;
import com.example.techassist.DTO.AppointmentDTO;
import com.example.techassist.DTO.CallDTO;
import com.example.techassist.DTO.HistoryDTO;
import com.example.techassist.Entities.*;
import com.example.techassist.Repositories.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.nio.file.Path;
import java.nio.file.Paths;

@SessionAttributes
@Controller
@AllArgsConstructor

public class TechnicianController {

    private final HttpSession httpSession;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ServiceFieldRepository serviceFieldRepository;
    @Autowired
    private TechnicianExperienceRepository technicianExperienceRepository;
    @Autowired
    private PhoneCallRepository phoneCallRepository;

    static int num = 0;

    @GetMapping(path = "/technicianHome")
    public String technicianHome() {
        return "technician/technicianHome";
    }

    @GetMapping(path = "/technicianProfile")
    public String technicianProfile(Model model) {
        String username = (String) httpSession.getAttribute("userName");
        var user = userRepository.findByUsername(username).orElse(null);
        List<ServiceField> serviceField = serviceFieldRepository.findAll();;
        model.addAttribute("serviceField", serviceField);
        model.addAttribute("user", user);
        return "technician/technicianProfile";
    }

    @Transactional
    @PostMapping(path = "/technicianProfile")
    public String save(@ModelAttribute User updatedUser, @ModelAttribute Technician updatedTechnician, @RequestParam("picture") MultipartFile file, BindingResult bindingResult, ModelMap mm) {

        if (bindingResult.hasErrors()) {
            mm.addAttribute("technician", updatedTechnician);
            return "technician/technicianProfile";
        } else {
            // Save the technician data to the database
            String username = (String) httpSession.getAttribute("userName");
            var user = userRepository.findByUsername(username).orElse(null);
            user.setUsername(updatedUser.getUsername());
            user.setName(updatedUser.getName());
            var technician = technicianRepository.findById(user.getTechnician().getId()).orElse(null);
            technician.setRate(updatedTechnician.getRate());
            technician.setJob_description(updatedTechnician.getJob_description());
            if (!file.isEmpty()) {
                try {
                    if(!user.getTechnician().getImage_url().isEmpty()){
                        Path oldImagePath = Paths.get("src/main/resources/static/images/user/" + user.getTechnician().getImage_url());
                        if (Files.exists(oldImagePath)) {
                            try {
                                Files.delete(oldImagePath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Path uploadDirectory = Paths.get("src/main/resources/static/images/user/");
                    if (!Files.exists(uploadDirectory)) {
                        try {
                            Files.createDirectories(uploadDirectory);
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Handle directory creation failure if needed
                        }
                    }
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(file.getOriginalFilename());
                    String fileName = UUID.randomUUID()+ "." + path.getFileName()
                                                                .toString()
                                                                .substring(
                                                                        path.getFileName()
                                                                                    .toString()
                                                                                    .lastIndexOf('.') + 1
                                                                    );
                    Files.write(Paths.get("src/main/resources/static/images/user/" +  fileName), bytes);
                    technician.setImage_url("/images/user/" +  fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            userRepository.save(user);
            technicianRepository.save(technician);
            List<ServiceField> serviceField = serviceFieldRepository.findAll();;
            mm.addAttribute("serviceField", serviceField);
            mm.addAttribute("user", user);
            mm.addAttribute("successMessage", "Data successfully saved");
            return "technician/technicianProfile";
        }
    }
    @GetMapping(path = "/technicianExperience/edit")
    public String technicianExperience(@RequestParam(required = false) Long id, Model model) {
        if(id != null){
            var experience = technicianExperienceRepository.findById(id).orElse(null);
            model.addAttribute("experience", experience);
        }
        return "technician/technicianExperience";
    }
    @PostMapping(path = "/technicianExperience/edit")
    public String technicianExperienceSave(@ModelAttribute TechnicianExperience updatedExperience, BindingResult bindingResult, ModelMap mm) {
        if (bindingResult.hasErrors()) {
            mm.addAttribute("experience", updatedExperience);
            return "technician/technicianExperience";
        } else {
            String username = (String) httpSession.getAttribute("userName");
            var user = userRepository.findByUsername(username).orElse(null);
            updatedExperience.setTechnician(technicianRepository.findById(user.getTechnician().getId()).orElse(null));
            technicianExperienceRepository.save(updatedExperience);
            mm.addAttribute("experience", updatedExperience);
            return "redirect:/technicianProfile";
        }
    }
    @GetMapping(path = "/availableTimeSetting")
    public String AvailableTimeSetting(Model model) {
        return "technician/availableTimeSetting";
    }

    @GetMapping(path = "/viewAppointment")
    public String viewAppointment(Model model) {
        String username = (String) httpSession.getAttribute("userName");
        var user = userRepository.findByUsername(username).orElse(null);
        var appointments = phoneCallRepository.findByTechnicianIdAndStartTimeGreaterThanEqual(user.getTechnician().getId(), LocalDateTime.now().minusHours(1));
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        for(var appointment : appointments){
            if(!isPassed(appointment)){
                var appointmentDTO = new AppointmentDTO();
                transformData(appointment, appointmentDTO);
                appointmentDTOs.add(appointmentDTO);
            }
        }
        model.addAttribute("appointments", appointmentDTOs);
        return "technician/viewAppointment";
    }

    @GetMapping(path = "/accountBalance")
    public String accountBalance(Model model) {
        String username = (String) httpSession.getAttribute("userName");
        var user = userRepository.findByUsername(username).orElse(null);
        var phoneCalls = phoneCallRepository.findByTechnicianIdAndStartTimeLessThanEqualAndStartTimeGreaterThan(user.getTechnician().getId(), LocalDateTime.now(), LocalDateTime.now().withDayOfMonth(1));
        AccountBalanceDTO AccountBalanceDTO = new AccountBalanceDTO();
        AccountBalanceDTO.callDTOList = new ArrayList<>();
        for(var phoneCall : phoneCalls) {
            if(isPassed(phoneCall)){
                var callDTO = new CallDTO();
                transformData(phoneCall, callDTO);
                callDTO.rating = phoneCall.getRating().toString();
                callDTO.review = phoneCall.getReview();
                callDTO.cost = phoneCall.getCost();
                AccountBalanceDTO.serviceFee += callDTO.cost;
                AccountBalanceDTO.callDTOList.add(callDTO);
            }
        }
        model.addAttribute("accountBalance", AccountBalanceDTO);
        return "technician/accountBalance";
    }

    @GetMapping(path = "/history")
    public String showHistory(Model model) {
        String username = (String) httpSession.getAttribute("userName");
        var user = userRepository.findByUsername(username).orElse(null);
        var phoneCalls = phoneCallRepository.findByTechnicianIdAndStartTimeLessThanEqual(user.getTechnician().getId(), LocalDateTime.now());
        List<HistoryDTO> historyDTOs = new ArrayList<>();
        for(var phoneCall : phoneCalls) {
            if(isPassed(phoneCall)){
                var historyDTO = new HistoryDTO();
                transformData(phoneCall, historyDTO);
                historyDTO.rating = phoneCall.getRating().toString();
                historyDTO.review = phoneCall.getReview();
                historyDTOs.add(historyDTO);
            }
        }
        model.addAttribute("histories", historyDTOs);
        return "technician/history";
    }

    private boolean isPassed(PhoneCall phoneCall) {
        LocalDateTime startTime = phoneCall.getStartTime().plusMinutes(phoneCall.getStartSlot().getDuration());
        LocalDateTime endTime = startTime.plusMinutes(phoneCall.getDurationSlot().getDuration());
        return endTime.isBefore(LocalDateTime.now());
    }
    private void transformData(PhoneCall phoneCall, AppointmentDTO appointmentDTO){
        appointmentDTO.date = phoneCall.getStartTime().getDayOfMonth() + "/"
                + phoneCall.getStartTime().getMonth().getValue() + "/"
                + phoneCall.getStartTime().getYear();
        appointmentDTO.startTime = phoneCall.getStartTime().getHour() + (phoneCall.getStartSlot().getDuration()/60)
                + ":" + String.format("%02d",(phoneCall.getStartSlot().getDuration() % 60));
        appointmentDTO.duration = phoneCall.getDurationSlot().getDuration().toString();
        appointmentDTO.customerName = phoneCall.getClient().getUser().getName();
    }


}

