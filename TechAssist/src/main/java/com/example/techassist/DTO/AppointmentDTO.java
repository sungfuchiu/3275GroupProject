package com.example.techassist.DTO;

import com.example.techassist.Entities.PhoneCall;

import java.time.LocalDateTime;

public class AppointmentDTO {
    public String date;
    public String startTime;
    public String duration;
    public String customerName;
    public String technicianName;
    public Boolean isReady;
    public long id;
    public static void transformData(PhoneCall phoneCall, AppointmentDTO appointmentDTO){
        appointmentDTO.date = phoneCall.getStartTime().getDayOfMonth() + "/"
                + phoneCall.getStartTime().getMonth().getValue() + "/"
                + phoneCall.getStartTime().getYear();
        appointmentDTO.startTime = phoneCall.getStartTime().getHour() + (phoneCall.getStartSlot().getDuration()/60)
                + ":" + String.format("%02d",(phoneCall.getStartSlot().getDuration() % 60));
        appointmentDTO.duration = phoneCall.getDurationSlot().getDuration().toString();
        appointmentDTO.customerName = phoneCall.getClient().getUser().getName();
        appointmentDTO.technicianName = phoneCall.getTechnician().getUser().getName();
        var callStartTime = phoneCall.getStartTime().plusMinutes(phoneCall.getStartSlot().getDuration()).plusMinutes(-10);
        var callEndTime = phoneCall.getStartTime().plusMinutes(phoneCall.getStartSlot().getDuration()).plusMinutes(phoneCall.getDurationSlot().getDuration());
        appointmentDTO.isReady = LocalDateTime.now().isAfter(callStartTime) && LocalDateTime.now().isBefore(callEndTime);
        appointmentDTO.id = phoneCall.getId();
    }
}
