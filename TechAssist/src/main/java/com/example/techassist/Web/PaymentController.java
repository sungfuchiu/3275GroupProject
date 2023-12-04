package com.example.techassist.Web;

import com.example.techassist.DTO.PaymentOrder;
import com.example.techassist.DTO.PhoneCallDTO;
import com.example.techassist.Entities.AvailableTime;
import com.example.techassist.Entities.PhoneCall;
import com.example.techassist.Repositories.*;
import com.example.techassist.Services.PaypalService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

@SessionAttributes
@AllArgsConstructor
@RestController
@RequestMapping(value = "/paypal")
@CrossOrigin(origins = "*")
public class PaymentController {
    private final HttpSession httpSession;
    @Autowired
    private PaypalService paypalService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhoneCallRepository phoneCallRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private AvailableTimeRepository availableTimeRepository;

    @PostMapping(value = "/init")
    public PaymentOrder createPayment(@RequestBody PhoneCallDTO phoneCallDTO, HttpServletRequest request) {
        String baseUrl = request.getRequestURL().toString();
        String requestURI = request.getRequestURI();
        baseUrl = baseUrl.substring(0, baseUrl.length() - requestURI.length());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDate date = LocalDate.parse(phoneCallDTO.startDate, dateFormatter);
        LocalTime time = LocalTime.parse(phoneCallDTO.startTime, timeFormatter);
        LocalDateTime startTime = LocalDateTime.of(date, time)
                .withMinute(0)
                .withSecond(0);

        var availableTimeId = (long)httpSession.getAttribute("availableTimeId");
        String username = (String) httpSession.getAttribute("userName");
        var user = userRepository.findByUsername(username).orElse(null);
        var phoneCall = new PhoneCall();
        var technician = technicianRepository.findById(phoneCallDTO.technicianId).orElse(null);
        phoneCall.setTechnician(technician);
        phoneCall.setClient(user.getClient());
        phoneCall.setStartTime(startTime);
        var startSlot = timeSlotRepository.findTimeSlotByDuration(phoneCallDTO.startSlot).orElse(null);
        phoneCall.setStartSlot(startSlot);
        var durationSlot = timeSlotRepository.findTimeSlotByDuration(phoneCallDTO.durationSlot).orElse(null);
        phoneCall.setDurationSlot(durationSlot);
        phoneCall.setCost(phoneCallDTO.cost);
        phoneCallRepository.save(phoneCall);
        httpSession.setAttribute("phoneCallID", phoneCall.getId());
        var availableTime = availableTimeRepository.findById(availableTimeId).orElse(null);
        LocalDateTime startDateTime = LocalDateTime.of(
                availableTime.getAvailableDate().getYear(),
                availableTime.getAvailableDate().getMonth().getValue(),
                availableTime.getAvailableDate().getDayOfMonth(),
                availableTime.getStartHour(),
                availableTime.getStart_slot().getDuration());
        var phoneCallStartTime = phoneCall.getStartTime().withMinute(phoneCall.getStartSlot().getDuration());
        if(!startDateTime.isEqual(phoneCallStartTime)){
            var newAvailableTime = new AvailableTime();
            newAvailableTime.setTechnician(technician);
            newAvailableTime.setAvailableDate(startDateTime.toLocalDate());
            newAvailableTime.setStartHour(startDateTime.getHour());
            var availableStartSlot = timeSlotRepository.findTimeSlotByDuration(startDateTime.getMinute()).orElse(null);
            newAvailableTime.setStart_slot(availableStartSlot);
            newAvailableTime.setEndHour(phoneCall.getStartTime().getHour());
            var phoneCallEndSlot = timeSlotRepository.findTimeSlotByDuration(phoneCall.getStartTime().getMinute()).orElse(null);
            newAvailableTime.setEnd_slot(phoneCallEndSlot);
            availableTimeRepository.save(newAvailableTime);
        }
        LocalDateTime endDateTime = LocalDateTime.of(
                availableTime.getAvailableDate().getYear(),
                availableTime.getAvailableDate().getMonth().getValue(),
                availableTime.getAvailableDate().getDayOfMonth(),
                availableTime.getEndHour(),
                availableTime.getEnd_slot().getDuration());
        var phoneCallEndTime = phoneCallStartTime.plusMinutes(phoneCall.getDurationSlot().getDuration());
        if(!endDateTime.isEqual(phoneCallEndTime)){
            var newAvailableTime = new AvailableTime();
            newAvailableTime.setTechnician(technician);
            newAvailableTime.setAvailableDate(endDateTime.toLocalDate());
            newAvailableTime.setStartHour(phoneCallEndTime.getHour());
            var phoneCallEndSlot = timeSlotRepository.findTimeSlotByDuration(phoneCallEndTime.getMinute()).orElse(null);
            newAvailableTime.setStart_slot(phoneCallEndSlot);
            newAvailableTime.setEndHour(endDateTime.getHour());
            var availableEndSlot = timeSlotRepository.findTimeSlotByDuration(endDateTime.getMinute()).orElse(null);
            newAvailableTime.setEnd_slot(availableEndSlot);
            availableTimeRepository.save(newAvailableTime);
        }
        availableTimeRepository.delete(availableTime);
        return paypalService.createPayment(phoneCallDTO.cost, baseUrl);
    }

//    @GetMapping(value = "/capture")
//    public CompletedOrder completePayment(@RequestParam("token") String token) {
//        return paypalService.completePayment(token);
//    }
}
