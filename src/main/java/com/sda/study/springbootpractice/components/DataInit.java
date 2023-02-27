package com.sda.study.springbootpractice.components;

import com.sda.study.springbootpractice.exceptions.SchoolNotFoundException;
import com.sda.study.springbootpractice.models.School;
import com.sda.study.springbootpractice.services.SchoolService;
import io.micrometer.observation.Observation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Component to initialize data on application startup
 */

@Component
public class DataInit {
    @Autowired
    private SchoolService schoolService;

    // @PostConstruct - then spring will call this method automatically
    @PostConstruct
    public void init() {
        initSchool();
    }

    // PRIVATE METHODS (always hold public methods above, then protected methods and then private methods
    private void initSchool() {
        System.out.println("Starting School initialization...");
        School school = new School();
        school.setName("Tartu University");
        school.setAddress("Ülikooli 18, Tartu");
        school.setPhone("+372 5555 5555");


        // logic to avoid duplication
        try {
            School searchSchool = schoolService.findSchoolByName(school.getName());
            System.out.println("Cannot pre-initialize school: " + school.getName());
        } catch (SchoolNotFoundException e) {
            schoolService.createSchool(school);
        }
    }


}
