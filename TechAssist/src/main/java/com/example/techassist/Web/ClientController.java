package com.example.techassist.Web;

import com.example.techassist.DAO.ClientDAO;
import com.example.techassist.DTO.AppointmentDTO;
import com.example.techassist.DTO.AvailableTimeDTO;
import com.example.techassist.DTO.CallDTO;
import com.example.techassist.Entities.AvailableTime;
import com.example.techassist.Entities.PhoneCall;
import com.example.techassist.Entities.ServiceField;
import com.example.techassist.Entities.TimeSlot;
import com.example.techassist.Repositories.*;
import com.example.techassist.Services.PaypalService;
import com.example.techassist.Utilities.ConstList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    @Autowired
    private PhoneCallRepository phoneCallRepository;
    @Autowired
    private AvailableTimeRepository availableTimeRepository;


    @GetMapping(path = "/clientHome")
    public String clientHome() {
        return "client/clientHome";
    }

    @GetMapping(path = "/findTechnician")
    public String findTechnician(ModelMap model) {
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

        return "client/findTechnician";
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

        return "client/findTechnician";
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
            dayOfWeek = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            sortMap.put(KEY_DAY_OF_WEEK, (String)formatedDate.get(KEY_NEW_DATE));
            model.addAttribute(KEY_SELECTED_DATE, selectedDate);
        }

        if(selectedTime != "") {
            sortMap.put(KEY_HOUR, selectedTime);
            model.addAttribute(KEY_SELECTED_HOUR, selectedTime);
        }

        serviceFieldList = serviceFieldRepository.findAll();
        technicianList = clientDAO.sortTechnician(Long.parseLong(serviceFieldId), sortMap);
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

        return "client/findTechnician";
    }

    @GetMapping(path = "/clientAppointment")
    public String clientAppointment(Model model) {
        String username = (String) httpSession.getAttribute("userName");
        var user = userRepository.findByUsername(username).orElse(null);
        var appointments = phoneCallRepository.findByClientIdAndStartTimeGreaterThanEqualOrderByStartTime(user.getClient().getId(), LocalDateTime.now().minusHours(1));
        List<AppointmentDTO> appointmentDTOs = new ArrayList<>();
        for(var appointment : appointments){
            if(!isPassed(appointment)){
                var appointmentDTO = new AppointmentDTO();
                AppointmentDTO.transformData(appointment, appointmentDTO);
                appointmentDTOs.add(appointmentDTO);
            }
        }
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("appointments", appointmentDTOs);
        return "client/viewAppointment";
    }

    private boolean isPassed(PhoneCall phoneCall) {
        LocalDateTime startTime = phoneCall.getStartTime().plusMinutes(phoneCall.getStartSlot().getDuration());
        LocalDateTime endTime = startTime.plusMinutes(phoneCall.getDurationSlot().getDuration());
        return endTime.isBefore(LocalDateTime.now());
    }

    @GetMapping(path = "/moveToTechnicianInfo")
    public String technicianInfo(ModelMap model, @RequestParam Long technicianId, HttpServletRequest request) {
        var technician = technicianRepository.findById(technicianId).orElse(null);
        httpSession.setAttribute("technicianId", technicianId);
        model.addAttribute("technician", technician);
        String previousPageUrl = request.getHeader("Referer");
        if(previousPageUrl.contains("getTechnician")){
            model.addAttribute("previousPageUrl", previousPageUrl);
            httpSession.setAttribute("previousPageUrl", previousPageUrl);
        }else{
            previousPageUrl = (String) httpSession.getAttribute("previousPageUrl");
            model.addAttribute("previousPageUrl", previousPageUrl);
        }
        return "client/technicianInfo";
    }

    @GetMapping(path = "/technicianAvailableTime")
    public String technicianAvailableTime(ModelMap model, HttpServletRequest request) {
        long technicianId = (long)httpSession.getAttribute("technicianId");
        var technician = technicianRepository.findById(technicianId).orElse(null);
        List<AvailableTime> availableTimes = availableTimeRepository.findAvailableTimeByTechnicianOrderByAvailableDateAscStartHourAsc(technician);
        List<AvailableTimeDTO> availableTimeDTOs = new ArrayList<>();
        for(var availableTime : availableTimes){
            AvailableTimeDTO availableTimeDTO = new AvailableTimeDTO();
            AvailableTimeDTO.transformData(availableTime, availableTimeDTO);
            availableTimeDTOs.add(availableTimeDTO);
        }
        model.addAttribute("availableTimeDTOs", availableTimeDTOs);
        String previousPageUrl = request.getHeader("Referer");
        if(previousPageUrl.contains("moveToTechnicianInfo")){
            model.addAttribute("previousPageUrl", previousPageUrl);
            httpSession.setAttribute("previousPageUrlForTechnicianAvailableTime", previousPageUrl);
        }else{
            previousPageUrl = (String) httpSession.getAttribute("previousPageUrlForTechnicianAvailableTime");
            model.addAttribute("previousPageUrl", previousPageUrl);
        }
        return "client/availableTime";
    }

    @GetMapping(path = "/reserveTime")
    public String reserveTime(ModelMap model, @RequestParam Long availableTimeId, HttpServletRequest request) {
        httpSession.setAttribute("availableTimeId", availableTimeId);
        AvailableTime availableTime = availableTimeRepository.findById(availableTimeId).orElse(null);
        model.addAttribute("availableTime", availableTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.of(
                availableTime.getAvailableDate().getYear(),
                availableTime.getAvailableDate().getMonth().getValue(),
                availableTime.getAvailableDate().getDayOfMonth(),
                availableTime.getStartHour(),
                availableTime.getStart_slot().getDuration());
        model.addAttribute("startDateTime", startDateTime.format(formatter));
        LocalDateTime endDateTime = LocalDateTime.of(
                availableTime.getAvailableDate().getYear(),
                availableTime.getAvailableDate().getMonth().getValue(),
                availableTime.getAvailableDate().getDayOfMonth(),
                availableTime.getEndHour(),
                availableTime.getEnd_slot().getDuration());
        model.addAttribute("endDateTime", endDateTime.format(formatter));
        model.addAttribute("hours", generateHoursList(availableTime));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("date", startDateTime.format(dateFormatter));
        String previousPageUrl = request.getHeader("Referer");
        model.addAttribute("previousPageUrl", previousPageUrl);
        var technician = technicianRepository.findById((long)httpSession.getAttribute("technicianId")).orElse(null);
        model.addAttribute("technician", technician);
        return "client/reserveTime";
    }
    private List<Integer> generateHoursList(AvailableTime availableTime) {
        List<Integer> hoursList = new ArrayList<>();
        int endHour = availableTime.getEndHour();
        if(availableTime.getEnd_slot().getDuration() == 0){
            endHour--;
        }
        for (int i = availableTime.getStartHour(); i <= endHour; i++) {
            hoursList.add(i);
        }
        return hoursList;
    }


    @GetMapping(value = "/capture")
    public String completePayment(ModelMap model, @RequestParam("token") String token) {
        var completeOrder = paypalService.completePayment(token);
        long phoneCallId = (long) httpSession.getAttribute("phoneCallID");
        var phoneCall = phoneCallRepository.findById(phoneCallId).orElse(null);
        var callDTO = new CallDTO();
        AppointmentDTO.transformData(phoneCall, callDTO);
        callDTO.cost = phoneCall.getCost();
        if(completeOrder.IsSuccess()){
            model.addAttribute("callDTO", callDTO);
            return "client/completePayment";
        }else{
            phoneCallRepository.delete(phoneCall);
            return "client/cancelPayment";
        }
    }
    @GetMapping(value = "/cancel")
    public String cancelPayment(@RequestParam("token") String token) {
        long phoneCallId = (long) httpSession.getAttribute("phoneCallID");
        var phoneCall = phoneCallRepository.findById(phoneCallId).orElse(null);
        phoneCallRepository.delete(phoneCall);
        return "client/cancelPayment";
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
        return "completePayment";
    }

    @GetMapping(path="/transitionCall")
    public String moveToVideoCall(ModelMap model, String callId) {
        model.addAttribute("callId", callId);

        return "videoCall";
    }

    //Method
    private List<Map<String, Object>> getAppointmentInfo(long clientId) {
        LocalDateTime zonedDateTime = LocalDateTime.now();
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(constList.FORMAT_DATE);
        String formattedDate = zonedDateTime.format(formatterDate);
        int currentHour = zonedDateTime.getHour();
        int currentMinute = zonedDateTime.getMinute();

        return clientDAO.selectAppointment(String.valueOf(clientId), formattedDate, currentHour, currentMinute);
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
