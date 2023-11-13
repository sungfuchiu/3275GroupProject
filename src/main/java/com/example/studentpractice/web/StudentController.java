package com.example.studentpractice.web;

import com.example.studentpractice.entities.Student;
import com.example.studentpractice.repositories.StudentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@SessionAttributes({"a", "e"})
@Controller
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    static int num = 0;

    @GetMapping(path = "/index")
    public String students(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        /*
        List<Student> students;
        if (keyword.isEmpty()) {
            students = studentRepository.findAll();
        } else {
            long key = Long.parseLong(keyword);
            students = studentRepository.findStudentById(key);
        }
        model.addAttribute("listStudents", students);
        */

        return "mainTomoya";
    }

    @GetMapping(path = "/")
    public String students2(Model model, ModelMap mm, @RequestParam(name = "keyword", defaultValue = "") String keyword, HttpSession session) {
       /*
        List<Student> students;
        if (keyword.isEmpty()) {
            students = studentRepository.findAll();
        } else {
            mm.put("e", 0);
            mm.put("a", 0);
            long key = Long.parseLong(keyword);
            students = studentRepository.findStudentById(key);
        }
        model.addAttribute("listStudents", students);

        */
        return "MainTomoya";
    }

    @GetMapping("/delete")
    public String delete(Long id) {
        studentRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/formStudents")
    public String formStudents(Model model) {
        model.addAttribute("student", new Student());
        return "formStudents";
    }

    @GetMapping("/createaccount")
    public String createaccount(Model model) {
        model.addAttribute("student", new Student());
        return "createaccount";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("student", new Student());
        return "login";
    }


    @GetMapping("/technicianinfo")
    public String technicianinfo(Model model) {
        model.addAttribute("student", new Student());
        return "technicianinfo";
    }

    @GetMapping("/mainTomoya")
    public String mainTomoya(Model model) {
        model.addAttribute("student", new Student());
        return "mainTomoya";
    }

    @GetMapping("/confirmation")
    public String confirmation(Model model) {
        model.addAttribute("student", new Student());
        return "confirmation";
    }

    @GetMapping("/payment")
    public String payment(Model model) {
        model.addAttribute("student", new Student());
        return "payment";
    }

    @GetMapping("/complete")
    public String complete(Model model) {
        model.addAttribute("student", new Student());
        return "complete";
    }

    @PostMapping(path = "/save")
    public String save(
            Model model,
            Student student,
            BindingResult bindingResult,
            ModelMap mm,
            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "formStudents";
        } else {
            studentRepository.save(student);
            if (num == 2) {
                mm.put("e", 2);
                mm.put("a", 0);
            } else {
                mm.put("a", 1);
                mm.put("e", 0);
            }
            return "redirect:index";
        }
    }

    @GetMapping("/editStudents")
    public String editStudents(Model model, Long id, HttpSession session) {
        num = 2;
        session.setAttribute("info", 0);
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) throw new RuntimeException("Patient does not exist");
        model.addAttribute("student", student);
        return "editStudents";
    }
}
