package com.example.techassist.Web;

import com.example.techassist.DAO.ClientDAO;
import com.example.techassist.DTO.CompletedOrder;
import com.example.techassist.DTO.PaymentOrder;
import com.example.techassist.Entities.ServiceField;
import com.example.techassist.Entities.Technician;
import com.example.techassist.Entities.TimeSlot;
import com.example.techassist.Repositories.ServiceFieldRepository;
import com.example.techassist.Repositories.TechnicianRepository;
import com.example.techassist.Repositories.TimeSlotRepository;
import com.example.techassist.Repositories.UserRepository;
import com.example.techassist.Services.PaypalService;
import com.example.techassist.Utilities.ConstList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

import static com.example.techassist.Utilities.ConstList.*;

@SessionAttributes
@Controller
@AllArgsConstructor

public class ClientController {
    private final HttpSession httpSession;
    List<ServiceField> serviceFieldList;
    List<TimeSlot> timeSlotList;
    List<Map<String, Object>> technicianList;
    List<Map<String, Object>> appointmentInfo;
    @Autowired
    ClientDAO clientDAO;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TechnicianRepository technicianRepository;
    @Autowired
    ServiceFieldRepository serviceFieldRepository;
    @Autowired
    TimeSlotRepository timeSlotRepository;
    @Autowired
    ConstList constList;
    @Autowired
    private PaypalService paypalService;

    @GetMapping(path = "/clientHome")
    public String clientHome(ModelMap model) {
        String userName = (String)httpSession.getAttribute(constList.KEY_USER_NAME);
        long clientId = 0;
        String callId = null;
        String appointment = null;
        var user = userRepository.findByUsername(userName);
        clientId = user.get().getClient().getId();

        appointmentInfo = getAppointmentInfo(clientId);

        if(appointmentInfo.size() != 0) {
            callId = appointmentInfo.get(0).get(constList.KEY_ID).toString();
            appointment = appointmentInfo.get(0).get(constList.KEY_APPOINTMENT_DATE).toString();
        }

        serviceFieldList = serviceFieldRepository.findAll();

        model.addAttribute(constList.KEY_USER_NAME, userName);
        model.addAttribute(constList.KEY_SERVICE_FIELD_LIST, serviceFieldList);

        if(callId !=  null) {
            model.addAttribute(constList.KEY_CALL_ID, callId);
        }

        if(appointment != null) {
            model.addAttribute(constList.KEY_APPOINTMENT_DATE, appointment);
        }

        httpSession.setAttribute(constList.KEY_USER_NAME, userName);

        return "client/clientHome";
    }

    @GetMapping(path="/getTechnician")
    public String getTechnicianList(ModelMap model, @RequestParam String serviceFieldId, @RequestParam String serviceFieldName) {
        String userName = (String)httpSession.getAttribute(constList.KEY_USER_NAME);
        long clientId = 0;
        String callId = null;
        String appointment = null;
        var user = userRepository.findByUsername(userName);
        clientId = user.get().getClient().getId();

        serviceFieldList = serviceFieldRepository.findAll();
        technicianList = clientDAO.selectTechnician(serviceFieldId);
        timeSlotList = timeSlotRepository.findAll();
        appointmentInfo = getAppointmentInfo(clientId);

        if(appointmentInfo.size() != 0) {
            callId = appointmentInfo.get(0).get(constList.KEY_ID).toString();
            appointment = appointmentInfo.get(0).get(constList.KEY_APPOINTMENT_DATE).toString();
        }

        model.addAttribute(constList.KEY_SERVICE_FIELD_LIST, serviceFieldList);
        model.addAttribute(constList.KEY_TECHNICIAN_LIST, technicianList);
        model.addAttribute(constList.KEY_TIME_SLOT_LIST, timeSlotList);
        model.addAttribute(constList.KEY_SELECTED_FIELD, serviceFieldName);

        if(callId != null) {
            model.addAttribute(constList.KEY_CALL_ID, callId);
        }

        if(appointment != null) {
            model.addAttribute(constList.KEY_APPOINTMENT_DATE, appointment);
        }
        httpSession.setAttribute(constList.KEY_USER_NAME, userName);
        httpSession.setAttribute(constList.KEY_SELECTED_FIELD, serviceFieldId);

        return "client/clientHome";
    }

    @GetMapping(path="/sortTechnician")
    public String sortTechnician(ModelMap model, String selectedDate, String selectedTime) {
        String userName = (String)httpSession.getAttribute(constList.KEY_USER_NAME);
        String serviceFieldId = (String)httpSession.getAttribute(constList.KEY_SELECTED_FIELD);
        long clientId = 0;
        String callId = null;
        String appointment = null;
        var user = userRepository.findByUsername(userName);
        clientId = user.get().getClient().getId();
        Map<String, String> sortMap = new HashMap<>();
        Map<String, Object> formatedDate = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        String dayOfWeek = null;
        String serviceFieldName = serviceFieldRepository.findById(Long.parseLong(serviceFieldId)).get().getField();

        if(selectedDate != "") {
            formatedDate = formatDate(selectedDate);
            calendar.setTime((Date) formatedDate.get(KEY_CALENDAR_DATE));
            dayOfWeek = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
            sortMap.put(KEY_DAY_OF_WEEK, dayOfWeek);
            model.addAttribute(KEY_SELECTED_DATE, selectedDate);
        }

        if(selectedTime != "") {
            sortMap.put(KEY_HOUR, selectedTime);
            model.addAttribute(KEY_SELECTED_HOUR, selectedTime);
        }

        serviceFieldList = serviceFieldRepository.findAll();
        technicianList = clientDAO.sortTechnician(serviceFieldId, sortMap);
        timeSlotList = timeSlotRepository.findAll();
        appointmentInfo = getAppointmentInfo(clientId);

        if(appointmentInfo.size() != 0) {
            callId = appointmentInfo.get(0).get(constList.KEY_ID).toString();
            appointment = appointmentInfo.get(0).get(constList.KEY_APPOINTMENT_DATE).toString();
        }

        model.addAttribute(constList.KEY_SERVICE_FIELD_LIST, serviceFieldList);
        model.addAttribute(constList.KEY_TECHNICIAN_LIST, technicianList);
        model.addAttribute(constList.KEY_TIME_SLOT_LIST, timeSlotList);
        model.addAttribute(constList.KEY_SELECTED_FIELD, serviceFieldName);

        if(callId != null) {
            model.addAttribute(constList.KEY_CALL_ID, callId);
        }

        if(appointment != null) {
            model.addAttribute(constList.KEY_APPOINTMENT_DATE, appointment);
        }

        httpSession.setAttribute(constList.KEY_USER_NAME, userName);
        httpSession.setAttribute(constList.KEY_SELECTED_FIELD, serviceFieldId);

        return "client/clientHome";
    }

    @GetMapping(path = "/moveToTechnicianInfo")
    public String technicianInfo(ModelMap model, @RequestParam Long technicianId, HttpServletRequest request) {
        String userName = (String)httpSession.getAttribute(constList.KEY_USER_NAME);
        var technician = technicianRepository.findById(technicianId).orElse(null);
        String previousPageUrl = request.getHeader("Referer");
        model.addAttribute("technician", technician);
        model.addAttribute("previousPageUrl", previousPageUrl);
        return "client/technicianInfo";
    }
//    @RestController
//    @PostMapping(value = "/init")
//    public PaymentOrder createPayment(
//            @RequestParam("sum") BigDecimal sum) {
//        return paypalService.createPayment(sum);
//    }
//
//    @PostMapping(value = "/capture")
//    public CompletedOrder completePayment(@RequestParam("token") String token) {
//        return paypalService.completePayment(token);
//    }
    @PostMapping(value = "/cancel")
    public String cancelPayment(@RequestParam("token") String token) {
        return "redirect:/login";
    }

    @GetMapping(path = "/confirmation")
    public String confirmation() {
        return "client/confirmation";
    }

    @GetMapping(path = "/payment")
    public String payment() {
        return "client/payment";
    }

    @GetMapping(path = "/complete")
    public String complete() {
        return "client/complete";
    }

    @GetMapping(path="/transitionCall")
    public String moveToVideoCall(ModelMap model, String callId) {
        model.addAttribute("callId", callId);

        return "videoCall";
    }

    //Method
    private List<Map<String, Object>> getAppointmentInfo(long clientId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(constList.FORMAT_DATE);
        String date = localDate.format(formatterDate);
        int currentHour = localDateTime.getHour();
        int currentMinute = localDateTime.getMinute();

        return clientDAO.selectAppointment(String.valueOf(clientId), date, currentHour, currentMinute);
    }

    private Map<String, Object> formatDate(String date) {
        String[] arr = date.split("/");
        String newDate = null;
        Date calendarDate = null;
        Map<String, Object> map = new HashMap<>();

        newDate = arr[2] + "-";
        newDate += arr[0] + "-";
        newDate += arr[1];

        calendarDate = new Date(Integer.parseInt(arr[2]), Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));

        map.put(KEY_NEW_DATE, newDate);
        map.put(KEY_CALENDAR_DATE, calendarDate);

        return map;
    }

}
