package com.gabriels.school.controller;

import com.gabriels.school.model.Person;
import com.gabriels.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DashboardController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value="/dashboard",method= RequestMethod.GET)
    public String displayDashboard(Model model, Authentication authentication, HttpSession httpSession){
        Person person=personRepository.readByEmail(authentication.getName());
        model.addAttribute("username",person.getName());
        model.addAttribute("roles",authentication.getAuthorities().toString());
        httpSession.setAttribute("loggedInPerson",person);
        //Below throw exception is to demo the exception handling in the application
        //throw new RuntimeException("Its been a bad day!");
        return "dashboard";
    }
}
