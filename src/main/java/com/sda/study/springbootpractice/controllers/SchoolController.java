package com.sda.study.springbootpractice.controllers;

import com.sda.study.springbootpractice.exceptions.CourseNotFoundException;
import com.sda.study.springbootpractice.exceptions.SchoolNotFoundException;
import com.sda.study.springbootpractice.models.School;
import com.sda.study.springbootpractice.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

//
//    @GetMapping("/create")
//    public String createSchool(Model model) {
//        model.addAttribute("school", new School());
//        return "school/create-school";
//    }

    //    @PostMapping("school/create")
    @GetMapping("/create")
    public String addCreateSchool(@ModelAttribute("school") School school, RedirectAttributes redirectAttributes) {
        try {
            schoolService.createSchool(school);
            redirectAttributes.addFlashAttribute("message", "School added successfully!");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getLocalizedMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/school/create";
        }
    }

    // Updating existing school
//    @GetMapping("/update/{id}")
//    public String showSchoolUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
//        try {
//            model.addAttribute("school", schoolService.findSchoolById(id));
//            return "school/update-school";
//        } catch (SchoolNotFoundException e) {
//            return handleException(redirectAttributes, e);
//        }
//    }

    //    @PostMapping("/update/{id}")
    @GetMapping("/update")
    public String updateSchool(@PathVariable Long id, @ModelAttribute("school") School school, RedirectAttributes redirectAttributes) {
        try {
            schoolService.updateSchool(id, school);
            redirectAttributes.addFlashAttribute("message", String.format("School #%d updated successfully!", id));
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school";
        } catch (SchoolNotFoundException e) {
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
