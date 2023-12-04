package com.example.techassist.DTO;

import com.example.techassist.Entities.AvailableTime;

import java.time.LocalTime;

public class AvailableTimeDTO {
    public String Date;
    public String StartTime;
    public String EndTime;
    public long ID;
    public static void transformData(AvailableTime availableTime, AvailableTimeDTO availableTimeDTO){
        availableTimeDTO.ID = availableTime.getId();
        availableTimeDTO.Date = availableTime.getAvailableDate().toString();
        availableTimeDTO.StartTime = LocalTime.of(availableTime.getStartHour(), availableTime.getStart_slot().getDuration()).toString();
        availableTimeDTO.EndTime = LocalTime.of(availableTime.getEndHour(), availableTime.getEnd_slot().getDuration()).toString();
    }
}
