package com.gabriels.school.controller;

import com.gabriels.school.model.Person;
import com.gabriels.school.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.invoke.MethodType;

@Slf4j
@Controller
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String registerPage(Model model) {
        model.addAttribute("person", new Person());
        return "register";
    }

    @RequestMapping(value="/createUser", method = {RequestMethod.POST})
    public String createUser(@Validated @ModelAttribute("person") Person person, Errors errors){
        if(errors.hasErrors())
            return "register";
        boolean isSaved=personService.savePerson(person);
        if(isSaved)
            return "redirect:/login?register=true";
        else
            return "register";
    }
}
