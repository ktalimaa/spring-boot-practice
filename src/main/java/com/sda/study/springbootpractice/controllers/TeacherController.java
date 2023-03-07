package com.sda.study.springbootpractice.controllers;

import com.sda.study.springbootpractice.exceptions.CourseNotFoundException;
import com.sda.study.springbootpractice.exceptions.TeacherNotFoundException;
import com.sda.study.springbootpractice.models.Teacher;
import com.sda.study.springbootpractice.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<Teacher> findAllTeachers() {
        return teacherService.findAllTeachers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findTeacherById(@PathVariable Long id) throws TeacherNotFoundException {
        Teacher teacher = teacherService.findTeacherById(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setDate(new Date().toInstant());
        return new ResponseEntity<>(teacher, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) throws CourseNotFoundException, TeacherNotFoundException {
        teacherService.deleteTeacherById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createTeacher(@RequestBody Teacher teacher) {
        try {
            Teacher searchTeacher = teacherService.findTeacherByName(teacher.getName());
            throw new RuntimeException("Teacher already exists! Cannot create this teacher!");
        } catch (TeacherNotFoundException e) {
            teacherService.createTeacher(teacher);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTeacher(@RequestBody Teacher teacher) throws TeacherNotFoundException {
        teacherService.updateTeacher(teacher);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/restore/{id}")
    public ResponseEntity<?> restoreTeacher(@PathVariable Long id) throws CourseNotFoundException, TeacherNotFoundException {
        teacherService.restoreTeacherById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
