package com.gabriels.school.controller;

import com.gabriels.school.model.Contact;
import com.gabriels.school.service.ContactService;
import jakarta.validation.Valid;
import org.apache.coyote.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    private static final Logger log=LoggerFactory.getLogger(ContactController.class);
    private final ContactService contactService;

    @Autowired
    private ContactController(ContactService contactService){
        this.contactService=contactService;
    }

    @RequestMapping(value = {"/contact"})
    public String displayContactPage(Model model){
        model.addAttribute("contact",new Contact());
        return "contact";
    }

    @RequestMapping(value={"/saveMsg"}, method = RequestMethod.POST)
    public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors){
        if(errors.hasErrors()){
            log.error("Error occurred while saving contact details: "+errors.getAllErrors());
            return "contact.html";
        }
        contactService.saveMessageDetails(contact);
        return"redirect:/contact";
    }

    @RequestMapping(value={"/displayMessages"},method=RequestMethod.GET)
    public ModelAndView displayMessage(Model model){
        ModelAndView modelAndView=new ModelAndView("messages.html");
        modelAndView.addObject("contactMsgs",contactService.findMessagesWithOpenStatus());
        return modelAndView;
    }

    @RequestMapping(value={"/closeMsg"}, method= RequestMethod.GET)
    public String closeMessage(@RequestParam int id){
        log.info("Closing message with id: {}", id);
        contactService.updateMessage(id);
        return "redirect:/displayMessages";
    }
}
