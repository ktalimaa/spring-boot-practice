package com.sda.study.springbootpractice.services.implementations;

import com.sda.study.springbootpractice.exceptions.CourseNotFoundException;
import com.sda.study.springbootpractice.exceptions.SchoolNotFoundException;
import com.sda.study.springbootpractice.models.Course;
import com.sda.study.springbootpractice.models.School;
import com.sda.study.springbootpractice.repositories.SchoolRepository;
import com.sda.study.springbootpractice.services.CourseService;
import com.sda.study.springbootpractice.services.SchoolService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of SchoolService
 */

// transactional makes db operations smoother
@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {
    // @autowired - every time when using schoolRepository it will automatically find a way to make connection
    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private CourseService courseService;

    @Override
    public void createSchool(School school) {
        school.setActive(true);
        schoolRepository.save(school);
    }

    @Override
    public School findSchoolById(Long id) throws SchoolNotFoundException {
        Optional<School> schoolOptional = schoolRepository.findById(id);

        if (schoolOptional.isEmpty()) {
            throw new SchoolNotFoundException(id);
        }
        return schoolOptional.get();
    }

    @Override
    public School findSchoolByName(String name) throws SchoolNotFoundException {
        Optional<School> schoolOptional = schoolRepository.findByName(name); // made Optional<School> findByName(String name); into SchoolRepository

        if (schoolOptional.isEmpty()) {
            throw new SchoolNotFoundException(name);
        }
        return schoolOptional.get();
    }

    @Override
    public List<School> findAllSchools() {
        return schoolRepository.findAll();
    }

    @Override
    public void updateSchool(Long id, School school) throws SchoolNotFoundException {
        if (findSchoolById(school.getId()) != null) {
            schoolRepository.saveAndFlush(school);
        }
    }

    @Override
    public void deleteSchoolById(Long id) throws SchoolNotFoundException, CourseNotFoundException {
        School school = findSchoolById(id);
        // schoolRepository.delete(school);    // to delete the record completely from the repository
        school.setActive(false);
        schoolRepository.saveAndFlush(school);

        // find all the course belong to the school and delete the respective courses
        for (Course course : courseService.findAllCoursesBySchool(school)) {
            courseService.deleteCourseById(course.getId());
        }
    }

    @Override
    public void restoreSchoolById(Long id) throws SchoolNotFoundException, CourseNotFoundException {
        School school = findSchoolById(id);
        school.setActive(true);
        schoolRepository.saveAndFlush(school);

        // find all the course belong to the school and delete the respective courses
        for (Course course : courseService.findAllCoursesBySchool(school)) {
            courseService.restoreCourseById(course.getId());
        }
    }
}
