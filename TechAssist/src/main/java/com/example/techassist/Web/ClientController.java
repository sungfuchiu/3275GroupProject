package com.example.techassist.Web;

import com.example.techassist.DAO.ClientDAO;
import com.example.techassist.Entities.ServiceField;
import com.example.techassist.Entities.Technician;
import com.example.techassist.Entities.TimeSlot;
import com.example.techassist.Repositories.ServiceFieldRepository;
import com.example.techassist.Repositories.TechnicianRepository;
import com.example.techassist.Repositories.TimeSlotRepository;
import com.example.techassist.Repositories.UserRepository;
import com.example.techassist.Utilities.ConstList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@SessionAttributes
@Controller
@AllArgsConstructor

public class ClientController {
    private final HttpSession httpSession;
    List<ServiceField> serviceFieldList;
    List<TimeSlot> timeSlotList;
    List<Map<String, Object>> technicianList;
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

    @GetMapping(path = "/clientHome")
    public String clientHome(ModelMap model) {
        String userName = (String)httpSession.getAttribute(constList.KEY_USER_NAME);
        long clientId = 0;
        long callId = 0;
        String appointment = null;
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = localDateTime.toLocalDate();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(constList.FORMAT_DATE);
        String date = localDate.format(formatterDate);
        int currentHour = localDateTime.getHour();
        int currentMinute = localDateTime.getMinute();

        var user = userRepository.findByUsername(userName);
        clientId = user.get().getClient().getId();

//        appointmentInfo = clientDAO.selectAppointment(clientId, date, currentHour, currentMinute);
//
//        if(appointmentInfo.size() != 0) {
//            callId = appointmentInfo.get(0).get(constList.KEY_ATTRIBUTE_ID).toString();
//            appointment = appointmentInfo.get(0).get(constList.KEY_ATTRIBUTE_TIME).toString();
//        }

        serviceFieldList = serviceFieldRepository.findAll();

        model.addAttribute(constList.KEY_USER_NAME, userName);
        model.addAttribute(constList.KEY_SERVICE_FIELD_LIST, serviceFieldList);

        if(callId != 0) {
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
//        long callId = 0;
//        String appointment = null;
//        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDate localDate = localDateTime.toLocalDate();
//        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(constList.FORMAT_DATE);
//        String date = localDate.format(formatterDate);
//        int currentHour = localDateTime.getHour();
//        int currentMinute = localDateTime.getMinute();
//
        serviceFieldList = serviceFieldRepository.findAll();
        technicianList = clientDAO.selectTechnician(serviceFieldId);
        timeSlotList = timeSlotRepository.findAll();
//
        var user = userRepository.findByUsername(userName);
        clientId = user.get().getClient().getId();

//        appointmentInfo = clientDAO.selectAppointment(clientId, date, currentHour, currentMinute);
//
//        if(appointmentInfo.size() != 0) {
//            callId = appointmentInfo.get(0).get(constList.KEY_ATTRIBUTE_ID).toString();
//            appointment = appointmentInfo.get(0).get(constList.KEY_ATTRIBUTE_TIME).toString();
//        }

        model.addAttribute(constList.KEY_SERVICE_FIELD_LIST, serviceFieldList);
        model.addAttribute(constList.KEY_TECHNICIAN_LIST, technicianList);
        model.addAttribute(constList.KEY_TIME_SLOT_LIST, timeSlotList);
        model.addAttribute(constList.KEY_SELECTED_FIELD, serviceFieldName);
//        model.addAttribute(constList.KEY_USER_NAME, userName);
//        model.addAttribute(constList.KEY_SERVICE_FIELD_LIST, serviceFieldList);
//
//        if(callId != 0) {
//            model.addAttribute(constList.KEY_CALL_ID, callId);
//        }
//
//        if(appointment != null) {
//            model.addAttribute(constList.KEY_APPOINTMENT_DATE, appointment);
//        }
        httpSession.setAttribute(constList.KEY_USER_NAME, userName);

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


    //Client home
//    @GetMapping(path="/test")
//    public String initilaCustomerTop(ModelMap model, @RequestParam String clientId) {
//        String userName;
//        String callId = null;
//        String appointment = null;
//        LocalDateTime localDateTime = LocalDateTime.now();
//        LocalDate localDate = localDateTime.toLocalDate();
//        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(constList.FORMAT_DATE);
//        String date = localDate.format(formatterDate);
//        int currentHour = localDateTime.getHour();
//        int currentMinute = localDateTime.getMinute();
//
//        clientInfo = clientDAO.selectClient(clientId);
//        userName = clientInfo.get(0).get(constList.KEY_ATTRIBUTE_USER_NAME).toString();
//
////        appointmentInfo = clientDAO.selectAppointment(clientId, date, currentHour, currentMinute);
////
////        if(appointmentInfo.size() != 0) {
////            callId = appointmentInfo.get(0).get(constList.KEY_ATTRIBUTE_ID).toString();
////            appointment = appointmentInfo.get(0).get(constList.KEY_ATTRIBUTE_TIME).toString();
////        }
//
//        categoryList = clientDAO.selectCategory();
//
//        model.addAttribute(constList.KEY_ATTRIBUTE_CUSTOMER_NAME , userName);
//        model.addAttribute(constList.KEY_ATTRIBUTE_CATEGORY_LIST, categoryList);
//        if(callId != null) {
//            model.addAttribute(constList.KEY_ATTRIBUTE_CALL_ID, callId);
//        }
//        if(appointment != null) {
//            model.addAttribute(constList.KEY_ATTRIBUTE_APPOINTMENT_START, appointment);
//        }
//
//        model.addAttribute(constList.KEY_ATTRIBUTE_CLIENT_ID, clientId);
//
//        //test
//        model.addAttribute("callId", "test call");
//        //
//
//        return "customer_top";
//    }

//    @GetMapping(path="getTechnician")
//    public String getTechnicianList(ModelMap model, @RequestParam String categoryId, @RequestParam String categoryName) {
//        String clientId = (String)model.get(constList.KEY_ATTRIBUTE_CLIENT_ID);
//        String userName;
//
//        categoryInfo = clientDAO.selectCategory(categoryId);
//        model.addAttribute(constList.KEY_SESSION_CATEGORY_ID, categoryId);
//
//        technicianList = clientDAO.selectTechnician(categoryId);
//        timeslotList = clientDAO.selectTimeSlot();
//
//        clientInfo = clientDAO.selectClient(clientId);
//        userName = clientInfo.get(0).get(constList.KEY_ATTRIBUTE_USER_NAME).toString();
//
//        model.addAttribute(constList.KEY_ATTRIBUTE_CUSTOMER_NAME, userName);
//        model.addAttribute(constList.KEY_ATTRIBUTE_CATEGORY_LIST, categoryList);
//        model.addAttribute(constList.KEY_ATTRIBUTE_TECHNICIAN_LIST, technicianList);
//        model.addAttribute(constList.KEY_ATTRIBUTE_TIMESLOT_LIST, timeslotList);
//        model.addAttribute(constList.KEY_ATTRIBUTE_SELECTED_CATEGORY, categoryName);
//
//        return "customer_top";
//    }

    @GetMapping(path="/transitionCall")
    public String moveToVideoCall(ModelMap model, String callId) {
        model.addAttribute("callId", callId);

        return "videoCall";
    }
}
