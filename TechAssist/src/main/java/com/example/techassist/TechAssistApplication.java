package com.example.techassist;

import com.example.techassist.Entities.SetTR;
import com.example.techassist.Repositories.SetTRRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.example.techassist.Entities.Technician;
import com.example.techassist.Repositories.TechnicianRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.Date;

//(exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication
public class TechAssistApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechAssistApplication.class, args);
    }

    @Bean
    CommandLineRunner techCommandLineRunner(TechnicianRepository technicianRepository) {
        return args -> {
            technicianRepository.save(new Technician(null, "Jame", "Davis", 123456,"jame@gmail.com","burnaby BC","3 years",null,null));
                 technicianRepository.findAll().forEach(p -> {
                System.out.println(p.getFirstName());
            });
        };
    }

    @Bean
    CommandLineRunner setTRCommandLineRunner(SetTRRepository setTRRepository) {
        return args -> {
            setTRRepository.save(new SetTR(null, null, "12:30", "Unclog","15",35));
            setTRRepository.findAll().forEach(p -> {
                System.out.println(p.getServices());
            });
        };
    }
}
