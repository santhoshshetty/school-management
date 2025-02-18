package com.gabriels.school.controller;

import com.gabriels.school.model.EazyClass;
import com.gabriels.school.model.Person;
import com.gabriels.school.repository.EazyClassRepository;
import com.gabriels.school.repository.PersonRepository;
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

    @GetMapping(value={"/displayClasses"})
    public ModelAndView displayClasses(Model model){
        ModelAndView modelAndView=new ModelAndView("classes");
        List<EazyClass> eazyClasses=eazyClassRepository.findAll();
        modelAndView.addObject("eazyClass",eazyClasses);
        return modelAndView;
    }

    @RequestMapping(value="{/addNewClass}", method= RequestMethod.POST)
    public ModelAndView addNewClass(@Validated @ModelAttribute("eazyClass") EazyClass eazyClass){
        eazyClassRepository.save(eazyClass);
        return new ModelAndView("redirect:/admin/displayClasses");
    }


    @RequestMapping(value={"/deleteClass"})
    public ModelAndView deleteClasses(Model model, @RequestParam int id){
        Optional<EazyClass> deleteObject=eazyClassRepository.findById(id);
        deleteObject.ifPresent((value)-> {
                    for (Person person : value.getPersons()) {
                        person.setEazyClass(null);
                        personRepository.save(person);
                    }
                });
        deleteObject.ifPresent((value)->eazyClassRepository.delete(value));
        return new ModelAndView("redirect:/admin/displayClasses");
    }

}
