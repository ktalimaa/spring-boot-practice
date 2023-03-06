package com.sda.study.springbootpractice.controllers;

import com.sda.study.springbootpractice.exceptions.CourseNotFoundException;
import com.sda.study.springbootpractice.exceptions.SchoolNotFoundException;
import com.sda.study.springbootpractice.models.School;
import com.sda.study.springbootpractice.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @GetMapping
    public String showSchoolListPage(Model model, @ModelAttribute("message") String message, @ModelAttribute("messageType") String messageType) {
        model.addAttribute("schools", schoolService.findAllSchools());
        return "school/list-school";
    }

    //    localhost:8080/school/1 - pair variable
    @GetMapping("/{id}")
    public String showSchoolViewPage(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("school", schoolService.findSchoolById(id));
            return "school/view-school";
        } catch (SchoolNotFoundException e) {
            return handleException(redirectAttributes, e);
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteSchool(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            schoolService.deleteSchoolById(id);
            redirectAttributes.addFlashAttribute("message", String.format("School #%d deleted successfully!", id));
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school";
        } catch (SchoolNotFoundException | CourseNotFoundException e) {
            return handleException(redirectAttributes, e);
        }
    }

    // This method will show the create school form page; showing is always with @GetMapping
    @GetMapping("/create")
    public String showCreateSchoolPage(@ModelAttribute("school") School school,
                                       @ModelAttribute("message") String message,
                                       @ModelAttribute("messageType") String messageType) {
        return "school/create-school";      // create-school html page
    }

    //  Called when we press submit button in the create-school form
    @PostMapping
    public String createSchool(School school, RedirectAttributes redirectAttributes) {
        try {
            School searchSchool = schoolService.findSchoolByName(school.getName());
            redirectAttributes.addFlashAttribute("message", String.format("School(%s) already exists!", school.getName()));
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/school/create";       // submit button in create-school form
        } catch (SchoolNotFoundException e) {
            schoolService.createSchool((school));
            redirectAttributes.addFlashAttribute("message", String.format("School(%s) has been created successfully!", school.getName()));
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school";      // when no error, then it will go school-list page
        }
    }


    @GetMapping("/restore/{id}")
    public String restoreSchool(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            schoolService.restoreSchoolById(id);
            redirectAttributes.addFlashAttribute("message", String.format("School #%d restored successfully!", id));
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school";
        } catch (SchoolNotFoundException | CourseNotFoundException e) {
            return handleException(redirectAttributes, e);
        }
    }


    // PRIVATE METHODS //

    // reason for this extraction is better to have one code to call in different methods
    private String handleException(RedirectAttributes redirectAttributes, Exception e) {
        redirectAttributes.addFlashAttribute("message", e.getLocalizedMessage());
        redirectAttributes.addFlashAttribute("messageType", "error");
        return "redirect:/school";
    }

}
