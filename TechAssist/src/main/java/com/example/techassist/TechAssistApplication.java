package com.example.techassist;

import com.example.techassist.Entities.*;
import com.example.techassist.Repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//(exclude = {DataSourceAutoConfiguration.class })
@EnableTransactionManagement
@SpringBootApplication
public class TechAssistApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechAssistApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ServiceFieldRepository serviceFieldRepository,
                                        UserRepository userRepository,
                                        TechnicianRepository technicianRepository,
                                        TechnicianExperienceRepository technicianExperienceRepository) {
        return args -> {
            //Create User and Technician
            ManipulateUser(serviceFieldRepository, userRepository,technicianRepository, technicianExperienceRepository);
        };
    }

    @Transactional
    public void ManipulateUser(ServiceFieldRepository serviceFieldRepository,
                               UserRepository userRepository,
                               TechnicianRepository technicianRepository,
                               TechnicianExperienceRepository technicianExperienceRepository)
    {

        System.out.println("Create User.");
        var newServiceField = serviceFieldRepository.save(new ServiceField(null, "test"));
        var newTechnician = technicianRepository.save(
                new Technician("test description", 23.4F, "photo.jpg", newServiceField));
        var newTechnicianExperiences = newTechnician.getExperiences();
        var newUser = userRepository.save(new User("test@test.test", "test", "test", newTechnician, null));
        newTechnicianExperiences.add(technicianExperienceRepository.save(new TechnicianExperience(null, "pluming apprentice", "", 2000, 5, newTechnician )));
        newTechnicianExperiences.add(technicianExperienceRepository.save(new TechnicianExperience(null, "pluming master", "", 2006, 8, newTechnician )));
        technicianRepository.save(newTechnician);

        System.out.println("Read User.");
        User user = userRepository.findByUsername(newUser.getUsername()).orElse(null);
        System.out.printf("Username:%s, Name:%s, Password:%s%n", user.getUsername(), user.getName(), user.getPassword());
        System.out.printf("job_description:%s, rate:%f, image_url:%s%n", user.getTechnician().getJob_description(), user.getTechnician().getRate(), user.getTechnician().getImage_url());
        System.out.printf("0. experience_description:%s, start_year:%d, year:%d%n", user.getTechnician().getExperiences().get(0).getDescription(), user.getTechnician().getExperiences().get(0).getStart_year(), user.getTechnician().getExperiences().get(0).getYear());
        System.out.printf("1. experience_description:%s, start_year:%d, year:%d%n", user.getTechnician().getExperiences().get(1).getDescription(), user.getTechnician().getExperiences().get(1).getStart_year(), user.getTechnician().getExperiences().get(1).getYear());

        System.out.println("Update User.");
        user.setName("nameChanged");
        var changedTechnician = user.getTechnician();
        changedTechnician.setJob_description("descriptionChanged");
        technicianRepository.save(changedTechnician);
        var changedExperience0 = user.getTechnician().getExperiences().get(0);
        changedExperience0.setDescription("experience description 1 Changed");
        technicianExperienceRepository.save(changedExperience0);
        var changedExperience1 = user.getTechnician().getExperiences().get(1);
        changedExperience1.setDescription("experience description 1 Changed");
        technicianExperienceRepository.save(changedExperience1);
        System.out.printf("Username:%s, Name:%s, Password:%s%n", user.getUsername(), user.getName(), user.getPassword());
        System.out.printf("id:%d, job_description:%s, rate:%f, image_url:%s%n", user.getTechnician().getId(), user.getTechnician().getJob_description(), user.getTechnician().getRate(), user.getTechnician().getImage_url());
        System.out.printf("id:%d, experience_description:%s, start_year:%d, year:%d%n", user.getTechnician().getExperiences().get(0).getId(), user.getTechnician().getExperiences().get(0).getDescription(), user.getTechnician().getExperiences().get(0).getStart_year(), user.getTechnician().getExperiences().get(0).getYear());
        System.out.printf("id:%d, experience_description:%s, start_year:%d, year:%d%n", user.getTechnician().getExperiences().get(1).getId(), user.getTechnician().getExperiences().get(1).getDescription(), user.getTechnician().getExperiences().get(1).getStart_year(), user.getTechnician().getExperiences().get(1).getYear());

        System.out.println("Delete a User.");
        userRepository.delete(user);
        serviceFieldRepository.delete(newServiceField);
    }
}
