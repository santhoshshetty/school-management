package com.gabriels.school.controller;

import com.gabriels.school.model.Address;
import com.gabriels.school.model.Person;
import com.gabriels.school.model.Profile;
import com.gabriels.school.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class ProfileController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(value = {"/displayProfile"}, method = RequestMethod.GET)
    public ModelAndView displayProfile(HttpSession httpSession){
        Person person=(Person)httpSession.getAttribute("loggedInPerson");
        Profile profile=new Profile();
        profile.setName(person.getName());
        profile.setEmail(person.getEmail());
        profile.setMobileNumber(person.getMobileNumber());
        if(person.getAddress()!=null && person.getAddress().getAddressId()>0){
            profile.setAddress1(person.getAddress().getAddress1());
            profile.setAddress2(person.getAddress().getAddress2());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setCity(person.getAddress().getCity());
            profile.setState(person.getAddress().getState());
            profile.setZipCode(person.getAddress().getZip_code());
        }
        ModelAndView model=new ModelAndView("profile");
        model.addObject("profile",profile);
        return model;
    }

    @PostMapping(value={"/updateProfile"})
    public String updateProfile(@Validated @ModelAttribute("profile") Profile profile, Errors errors,
                                      HttpSession httpSession){
        if(errors.hasErrors())
            return "profile";
        Person person=(Person)httpSession.getAttribute("loggedInPerson");
        person.setName(profile.getName());
        person.setEmail(profile.getEmail());
        person.setMobileNumber(profile.getMobileNumber());
        if(person.getAddress()==null || !(person.getAddress().getAddressId()>0))
            person.setAddress(new Address());
        person.getAddress().setAddress1(profile.getAddress1());
        person.getAddress().setAddress2(profile.getAddress2());
        person.getAddress().setCity(profile.getCity());
        person.getAddress().setZip_code(profile.getZipCode());
        person.getAddress().setState(profile.getState());
        personRepository.save(person);
        httpSession.setAttribute("loggedInPerson",person);
        return "redirect:/displayProfile";
    }

}
