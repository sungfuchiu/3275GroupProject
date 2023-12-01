package com.example.techassist.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.techassist.Utilities.ConstList.KEY_DAY_OF_WEEK;
import static com.example.techassist.Utilities.ConstList.KEY_HOUR;

@Component
@Service
public class ClientDAO {
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

    public List<Map<String, Object>> selectAppointment(String clientId, String today, int currentHour, int currentMinutes) {
        sql.setLength(0);

        sql.append("SELECT p.id, ");
        sql.append("CONCAT(date(p.start_time), ' ', IF(p.start_slot = 4, HOUR(DATE_ADD(p.start_time, INTERVAL 1 HOUR)), HOUR(p.start_time)), ':' , IF(s.id = 4, '00', s.duration), ':00') as appointmentDate\n");
        sql.append("FROM phone_call as p \n");
        sql.append("INNER JOIN client as c \n");
        sql.append("ON p.client_id = c.id \n");
        sql.append("INNER JOIN time_slot as s \n");
        sql.append("ON p.start_slot = s.id \n");
        sql.append("WHERE c.id = '" + clientId + "'\n");
        sql.append("AND DATE(p.start_time) >= '" + today + "'\n");
        sql.append("AND IF(p.start_slot = 4, HOUR(DATE_ADD(p.start_time, INTERVAL 1 HOUR)), HOUR(p.start_time)) >= " + currentHour + "\n");
        sql.append("AND s.duration >= " + currentMinutes + "\n");
        sql.append("AND p.rating = 0\n");
        sql.append("ORDER BY \n");
        sql.append("IF(p.start_slot = 4, DATE_ADD(p.start_time, INTERVAL 1 HOUR), p.start_time) ASC, \n");
        sql.append("IF(p.start_slot = 4, 0, s.duration) ASC \n");
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

        sql.append("SELECT t.id as id, t.rate as rate, u.name as name ");
        sql.append("FROM technician as t ");
        sql.append("INNER JOIN user as u ");
        sql.append("ON t.id = u.technician_id ");
        sql.append("WHERE t.service_field_id = " + categoryId + " ");
        sql.append("ORDER BY t.rate DESC");

        return template.queryForList(sql.toString());
    }

    public List<Map<String, Object>> sortTechnician(String categoryId, Map<String, String> sortMap) {
        sql.setLength(0);

        sql.append("SELECT t.id as id, t.rate as rate, u.name as name ");
        sql.append("FROM technician as t ");
        sql.append("INNER JOIN user as u ");
        sql.append("ON t.id = u.technician_id ");
        sql.append("INNER JOIN available_time as a ");
        sql.append("ON t.id = a.technician_id ");
        sql.append("WHERE t.service_field_id = " + categoryId + " ");
        if(sortMap.containsKey(KEY_DAY_OF_WEEK)) {
            sql.append("AND a.week_day = " + sortMap.get(KEY_DAY_OF_WEEK) + " ");
        }
        if(sortMap.containsKey(KEY_HOUR)) {
            sql.append("AND a.hour = " + sortMap.get(KEY_HOUR) + " ");
        }
        sql.append("ORDER BY t.rate DESC");

        return template.queryForList(sql.toString());
    }

}
