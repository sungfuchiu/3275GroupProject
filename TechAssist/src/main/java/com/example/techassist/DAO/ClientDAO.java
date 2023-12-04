package com.example.techassist.DAO;

import com.example.techassist.Entities.Technician;
import com.example.techassist.Repositories.ServiceFieldRepository;
import com.example.techassist.Repositories.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.techassist.Utilities.ConstList.KEY_DAY_OF_WEEK;
import static com.example.techassist.Utilities.ConstList.KEY_HOUR;

@Component
@Service
public class ClientDAO {

    @Autowired
    private ServiceFieldRepository serviceFieldRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    JdbcTemplate template;
    StringBuilder sql = new StringBuilder();

    public ClientDAO(JdbcTemplate template) {
        this.template = template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public List<Map<String, Object>> selectClient(String clientId) {
        sql.setLength(0);

        sql.append("SELECT u.username, u.client_id \n");
        sql.append("FROM user as u \n");
        sql.append("INNER JOIN Client as c \n");
        sql.append("ON u.client_id = c.id \n");
        sql.append("WHERE c.id = '" + clientId + "'");

        return template.queryForList(sql.toString());
    }

    public List<Map<String, Object>> selectAppointment(String clientId, String date, int currentHour, int currentMinutes) {
        sql.setLength(0);

        sql.append("SELECT p.id, ");
        sql.append("CONCAT(date(p.start_time), ' ', IF(p.start_slot = 5, HOUR(DATE_ADD(p.start_time, INTERVAL 1 HOUR)), HOUR(p.start_time)), ':' , IF(s.duration = 0, 00, s.duration), ':00') as appointmentDate \n");
//        sql.append("p.start_time as appointmentDate \n");
        sql.append("FROM phone_call as p \n");
        sql.append("INNER JOIN client as c \n");
        sql.append("ON p.client_id = c.id \n");
        sql.append("INNER JOIN time_slot as s \n");
        sql.append("ON p.start_slot = s.id \n");
        sql.append("WHERE c.id = '" + clientId + "'\n");
        sql.append("AND DATE(p.start_time) >= '" + date + "'\n");
//        sql.append("AND IF(p.start_slot = 5, HOUR(DATE_ADD(p.start_time, INTERVAL 1 HOUR)), HOUR(p.start_time)) >= " + currentHour + "\n");
//        sql.append("AND HOUR(p.start_time) >= " + currentHour + " \n");
//        sql.append("AND s.duration >= " + currentMinutes + "\n");
        sql.append("AND p.rating IS NULL\n");
        sql.append("ORDER BY \n");
        sql.append("IF(p.start_slot = 5, DATE_ADD(p.start_time, INTERVAL 1 HOUR), p.start_time) ASC \n");
//        sql.append("IF(p.start_slot = 4, 0, s.duration) ASC \n");
//        sql.append("p.start_time ASC \n");
        sql.append("LIMIT 1");

        return template.queryForList(sql.toString());
    }

    public List<Map<String, Object>> selectCategory() {
        sql.setLength(0);

        sql.append("SELECT id, field ");
        sql.append("FROM service_field ");

        return template.queryForList(sql.toString());
    }

    public List<Map<String, Object>> selectCategory(String categoryId) {
        sql.setLength(0);

        sql.append("SELECT field ");
        sql.append("FROM service_field ");
        sql.append("WHERE id = " + categoryId);

        return template.queryForList(sql.toString());
    }

    public List<Map<String, Object>> selectTimeSlot() {
        sql.setLength(0);

        sql.append("SELECT * \n");
        sql.append("FROM time_Slot \n");
        sql.append("ORDER BY duration ASC");

        return template.queryForList(sql.toString());
    }

    public List<Map<String, Object>> selectTechnician(String categoryId) {
        sql.setLength(0);

        sql.append("SELECT t.id as id, t.rate as rate, u.name as name, t.image_url as image_url ");
        sql.append("FROM technician as t ");
        sql.append("INNER JOIN user as u ");
        sql.append("ON t.id = u.technician_id ");
        sql.append("WHERE t.service_field_id = " + categoryId + " ");
        sql.append("ORDER BY t.rate DESC");

        return template.queryForList(sql.toString());
    }

//    public List<Map<String, Object>> sortTechnician(String categoryId, Map<String, String> sortMap) {
//        sql.setLength(0);
//
//        sql.append("SELECT t.id as id, t.rate as rate, u.name as name ");
//        sql.append("FROM technician as t ");
//        sql.append("INNER JOIN user as u ");
//        sql.append("ON t.id = u.technician_id ");
//        sql.append("INNER JOIN available_time as a ");
//        sql.append("ON t.id = a.technician_id ");
//        sql.append("WHERE t.service_field_id = " + categoryId + " ");
//        if(sortMap.containsKey(KEY_DAY_OF_WEEK)) {
//            sql.append("AND a.week_day = " + sortMap.get(KEY_DAY_OF_WEEK) + " ");
//        }
//        if(sortMap.containsKey(KEY_HOUR)) {
//            sql.append("AND a.hour = " + sortMap.get(KEY_HOUR) + " ");
//        }
//        sql.append("ORDER BY t.rate DESC");
//
//        return template.queryForList(sql.toString());
//    }
    public List<Map<String, Object>> sortTechnician(long serviceFieldId, Map<String, String> sortMap) {
        List<Map<String, Object>> technicianDataList = new ArrayList<>();

        var serviceField = serviceFieldRepository.findById(serviceFieldId).orElse(null);
        List<Technician> technicianList = null;
        if(!sortMap.containsKey(KEY_DAY_OF_WEEK) || sortMap.get(KEY_DAY_OF_WEEK) == null) {
            technicianList = technicianRepository.findByServiceFieldOrderByRateAsc(serviceField);
        }else{
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String dateString = sortMap.get(KEY_DAY_OF_WEEK);
            LocalDate date = LocalDate.parse(dateString, formatter);
            if(!sortMap.containsKey(KEY_HOUR) || sortMap.get(KEY_HOUR) == null) {
                technicianList = technicianRepository.findByServiceFieldAndAvailableTimesAvailableDateEquals(serviceField, date);
            }else{
                int hour = Integer.parseInt(sortMap.get(KEY_HOUR));
                technicianList = technicianRepository.findByServiceFieldAndAvailableTimesAvailableDateEqualsAndAvailableTimesEndHourGreaterThanEqualAndAvailableTimesStartHourLessThanEqual(serviceField, date, hour, hour);
            }
        }

        for(var technician : technicianList){
            Map<String, Object> technicianData = new HashMap<>();
            technicianData.put("id", technician.getId());
            technicianData.put("rate", technician.getRate());
            technicianData.put("name", technician.getUser().getName());
            technicianDataList.add(technicianData);
        }
        return technicianDataList;
    }

}
