package com.example.techassist.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class RegisterDAO {
    @Autowired
    JdbcTemplate template;
    StringBuilder sql = new StringBuilder();

    public RegisterDAO(JdbcTemplate template) {
        this.template = template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public int checkUserDuplicate(String userName) {
        sql.setLength(0);

        sql.append("SELECT COUNT(username) FROM user WHERE username = '" + userName + "'");

        return template.queryForObject(sql.toString(), Integer.class);
    }

    public int insertClient() {
        sql.setLength(0);

        sql.append("INSERT INTO client () VALUES()");

        return template.update(sql.toString());
    }

    public int getNewClientId() {
        sql.setLength(0);

        sql.append("SELECT id FROM client ORDER BY id DESC LIMIT 1");

        return template.queryForObject(sql.toString(), Integer.class);
    }

    public int insertTechnician(String jobDescription, int serviceField) {
        sql.setLength(0);

        sql.append("INSERT INTO technician (job_description, rate, service_field_id, image_url) ");
        sql.append("VALUES ('" + jobDescription + "', ");
        sql.append("'" + 0 + "', ");
        sql.append("'" + serviceField + "', ");
        sql.append("'null'");
        sql.append(")");

        return template.update(sql.toString());
    }

    public int getNewTechnicianId() {
        sql.setLength(0);

        sql.append("SELECT id FROM technician ORDER BY id DESC LIMIT 1");

        return template.queryForObject(sql.toString(), Integer.class);
    }

    public int insertUser(String userName, String name, String password, int id, int userType) {
        sql.setLength(0);

        sql.append("INSERT INTO user ");
        sql.append("(username, name, password, ");
        if(userType == 0) {
            //client
            sql.append("client_id");
        } else {
            //technician
            sql.append("technician_id");
        }
        sql.append(") ");
        sql.append("VALUES ('" + userName + "', ");
        sql.append("'" + name + "', ");
        sql.append("'" + password + "', ");
        sql.append("'" + id + "'");
        sql.append(")");

        return template.update(sql.toString());
    }
}
