package com.gabriels.school.controller;

import com.gabriels.school.model.EazyClass;
import com.gabriels.school.model.Person;
import com.gabriels.school.repository.EazyClassRepository;
import com.gabriels.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@Data
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EazyClassRepository eazyClassRepository;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value = {"/displayClasses"}, method = RequestMethod.GET)
    public ModelAndView displayClasses(Model model) {
        ModelAndView modelAndView = new ModelAndView("classes");
        List<EazyClass> eazyClasses = eazyClassRepository.findAll();
        modelAndView.addObject("eazyClasses", eazyClasses);
        modelAndView.addObject("eazyClass", new EazyClass());
        return modelAndView;
    }

    @RequestMapping(value = {"/addNewClass"}, method = RequestMethod.POST)
    public ModelAndView addNewClass(@ModelAttribute("eazyClass") EazyClass eazyClass) {
        eazyClassRepository.save(eazyClass);
        return new ModelAndView("redirect:/admin/displayClasses");
    }


    @RequestMapping(value = {"/deleteClass"})
    public ModelAndView deleteClasses(Model model, @RequestParam int id) {
        Optional<EazyClass> deleteObject = eazyClassRepository.findById(id);
        deleteObject.ifPresent((value) -> {
            for (Person person : value.getPersons()) {
                person.setEazyClass(null);
                personRepository.save(person);
            }
        });
        deleteObject.ifPresent((value) -> eazyClassRepository.deleteById(id));
        return new ModelAndView("redirect:/admin/displayClasses");
    }

    @RequestMapping(value = {"/displayStudents"}, method = RequestMethod.GET)
    public ModelAndView displayStudents(Model model, @RequestParam int classId, HttpSession httpSession, @RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("students");
        Optional<EazyClass> eazyClass = eazyClassRepository.findById(classId);
        modelAndView.addObject("eazyClass", eazyClass.get());
        modelAndView.addObject("person", new Person());
        httpSession.setAttribute("eazyClass", eazyClass.get());
        if (error != null) {
            String errorMessage = "Invalid Email Entered";
            modelAndView.addObject("errorMessage", errorMessage);
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/addStudent"}, method = RequestMethod.POST)
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView();
        EazyClass eazyClass=(EazyClass) httpSession.getAttribute("eazyClass");
        Person personEntity = personRepository.readByEmail(person.getEmail());
        if (personEntity == null || !(personEntity.getPersonId() > 0)) {
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId()+"&error=true");
            return modelAndView;
        }
        personEntity.setEazyClass(eazyClass);
        personRepository.save(personEntity);
        eazyClass.getPersons().add(personEntity);
        eazyClassRepository.save(eazyClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eazyClass.getClassId());
        return modelAndView;
    }
}
